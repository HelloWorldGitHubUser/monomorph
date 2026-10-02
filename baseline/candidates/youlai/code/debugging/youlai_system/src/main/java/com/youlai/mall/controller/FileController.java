package com.youlai.mall.controller;

import com.youlai.mall.result.Result;
import com.youlai.mall.model.system.vo.FileInfoVO;
import com.youlai.mall.service.system.OssService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "06.文件接口")
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final OssService ossService;

    @PostMapping
    @Operation(summary= "文件上传")
    public Result<FileInfoVO> uploadFile(
            @Parameter(description = "表单文件对象") @RequestParam(value = "file") MultipartFile file
    ) {
        FileInfoVO fileInfo = ossService.uploadFile(file);
        return Result.success(fileInfo);
    }

    @DeleteMapping
    @Operation(summary= "文件删除")
    public Result deleteFile(
            @Parameter(description = "文件路径") @RequestParam String filePath
    ) {
        boolean result = ossService.deleteFile(filePath);
        return Result.judge(result);
    }

}




