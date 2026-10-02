package com.central.service;

import com.central.entity.FileInfo;
import com.central.model.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.Map;

/**
 * 文件服务接口
 * @author zlt
 */
public interface IFileService extends ISuperService<FileInfo> {
    /**
     * 上传文件
     */
    FileInfo upload(MultipartFile file) throws Exception;

    /**
     * 文件列表
     */
    PageResult<FileInfo> findList(Map<String, Object> params);

    /**
     * 删除文件
     */
    void delete(String id);

    /**
     * 输出文件
     */
    void out(String id, OutputStream os);
}





