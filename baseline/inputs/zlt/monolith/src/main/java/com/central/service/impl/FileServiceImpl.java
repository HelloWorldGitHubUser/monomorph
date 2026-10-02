package com.central.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.central.entity.FileInfo;
import com.central.mapper.FileMapper;
import com.central.model.PageResult;
import com.central.service.IFileService;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * 文件服务实现
 * 支持 MinIO 对象存储
 * @author zlt
 */
@Slf4j
@Service
public class FileServiceImpl extends SuperServiceImpl<FileMapper, FileInfo> implements IFileService {
    
    @Value("${zlt.file-server.type:local}")
    private String fileType;
    
    @Value("${zlt.file-server.s3.endpoint:}")
    private String endpoint;
    
    @Value("${zlt.file-server.s3.access-key:}")
    private String accessKey;
    
    @Value("${zlt.file-server.s3.access-key-secret:}")
    private String accessKeySecret;
    
    @Value("${zlt.file-server.s3.bucket-name:default}")
    private String bucketName;

    private MinioClient minioClient;

    @PostConstruct
    public void init() {
        if ("S3".equalsIgnoreCase(fileType) && StrUtil.isNotBlank(endpoint)) {
            try {
                minioClient = MinioClient.builder()
                        .endpoint(endpoint)
                        .credentials(accessKey, accessKeySecret)
                        .build();
                
                // 检查并创建 bucket
                boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
                if (!found) {
                    minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                }
                log.info("MinIO 客户端初始化成功: {}", endpoint);
            } catch (Exception e) {
                log.warn("MinIO 客户端初始化失败: {}", e.getMessage());
            }
        }
    }

    @Override
    public FileInfo upload(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String suffix = FileUtil.getSuffix(originalFilename);
        String objectName = IdUtil.fastSimpleUUID() + "." + suffix;
        
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(IdUtil.fastSimpleUUID());
        fileInfo.setName(originalFilename);
        fileInfo.setContentType(file.getContentType());
        fileInfo.setSize(file.getSize());
        fileInfo.setIsImg(isImage(file.getContentType()));
        fileInfo.setCreateTime(new Date());
        fileInfo.setUpdateTime(new Date());
        
        if (minioClient != null) {
            // 上传到 MinIO
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
            
            fileInfo.setPath(bucketName + "/" + objectName);
            fileInfo.setUrl(endpoint + "/" + bucketName + "/" + objectName);
            fileInfo.setSource("S3");
        } else {
            // 本地存储（简化版，实际需要配置存储路径）
            fileInfo.setPath("local/" + objectName);
            fileInfo.setUrl("/files/local/" + objectName);
            fileInfo.setSource("LOCAL");
            log.warn("MinIO 未配置，文件上传功能受限");
        }
        
        baseMapper.insert(fileInfo);
        return fileInfo;
    }

    @Override
    public PageResult<FileInfo> findList(Map<String, Object> params) {
        Integer pageNum = MapUtils.getInteger(params, "page", 1);
        Integer pageSize = MapUtils.getInteger(params, "limit", 10);
        
        Page<FileInfo> page = new Page<>(pageNum, pageSize);
        List<FileInfo> list = baseMapper.findList(page, params);
        
        return PageResult.<FileInfo>builder()
                .data(list)
                .code(0)
                .count(page.getTotal())
                .build();
    }

    @Override
    public void delete(String id) {
        FileInfo fileInfo = baseMapper.selectById(id);
        if (fileInfo != null) {
            // 删除存储中的文件
            if (minioClient != null && StrUtil.isNotBlank(fileInfo.getPath())) {
                try {
                    String[] parts = fileInfo.getPath().split("/", 2);
                    if (parts.length == 2) {
                        minioClient.removeObject(RemoveObjectArgs.builder()
                                .bucket(parts[0])
                                .object(parts[1])
                                .build());
                    }
                } catch (Exception e) {
                    log.error("删除 MinIO 文件失败: {}", e.getMessage());
                }
            }
            // 删除数据库记录
            baseMapper.deleteById(id);
        }
    }

    @Override
    public void out(String id, OutputStream os) {
        FileInfo fileInfo = baseMapper.selectById(id);
        if (fileInfo != null && minioClient != null) {
            try {
                String[] parts = fileInfo.getPath().split("/", 2);
                if (parts.length == 2) {
                    try (InputStream is = minioClient.getObject(GetObjectArgs.builder()
                            .bucket(parts[0])
                            .object(parts[1])
                            .build())) {
                        byte[] buffer = new byte[8192];
                        int len;
                        while ((len = is.read(buffer)) != -1) {
                            os.write(buffer, 0, len);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("输出文件失败: {}", e.getMessage());
            }
        }
    }

    private boolean isImage(String contentType) {
        return contentType != null && contentType.startsWith("image/");
    }
}





