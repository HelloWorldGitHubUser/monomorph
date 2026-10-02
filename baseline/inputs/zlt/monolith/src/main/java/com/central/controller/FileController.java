package com.central.controller;

import com.central.entity.FileInfo;
import com.central.model.PageResult;
import com.central.model.Result;
import com.central.service.IFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import java.util.Map;

/**
 * 文件管理控制器
 * 模拟网关路由前缀 /api-file
 * @author zlt
 */
@Tag(name = "文件管理")
@RestController
@RequestMapping("/api-file")
public class FileController {
    @Resource
    private IFileService fileService;

    /**
     * 文件上传
     * 根据fileType选择上传方式
     */
    @PostMapping("/files-anon")
    @Operation(summary = "文件上传")
    public FileInfo upload(@RequestParam("file") MultipartFile file) throws Exception {
        return fileService.upload(file);
    }

    /**
     * 文件删除
     */
    @DeleteMapping("/files/{id}")
    @Operation(summary = "文件删除")
    public Result delete(@PathVariable String id) {
        try {
            fileService.delete(id);
            return Result.succeed("操作成功");
        } catch (Exception ex) {
            return Result.failed("操作失败");
        }
    }

    /**
     * 文件查询
     */
    @GetMapping("/files")
    @Operation(summary = "文件列表")
    public PageResult<FileInfo> findFiles(@RequestParam Map<String, Object> params) {
        return fileService.findList(params);
    }
}





