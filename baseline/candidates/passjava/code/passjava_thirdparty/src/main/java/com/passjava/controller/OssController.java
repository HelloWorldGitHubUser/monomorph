package com.passjava.controller;

import com.passjava.utils.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * OSS 文件上传控制器
 * 
 * 单体版本：简化实现，返回 Mock 数据用于开发测试
 * 
 * 
 * @author 公众号：悟空聊架构
 * @site www.passjava.cn
 */
@RestController
@RequestMapping("/thirdparty/v1/admin/oss")
public class OssController {

    @Value("${oss.enabled:false}")
    private boolean ossEnabled;

    @Value("${oss.endpoint:oss-cn-beijing.aliyuncs.com}")
    private String endpoint;

    @Value("${oss.bucket:passjava}")
    private String bucket;

    /**
     * 获取 OSS 上传策略
     * 
     * 用于前端直传 OSS，返回签名后的上传凭证
     */
    @RequestMapping("/getPolicy")
    public R getPolicy() {
        String formatDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dir = formatDate + "/";
        
        Map<String, String> respMap = new LinkedHashMap<>();
        
        if (ossEnabled) {
            // 需要配置阿里云 OSS SDK 和访问密钥
            return R.error("OSS 功能未配置，请配置阿里云访问密钥");
        }
        
        // Mock 实现：返回模拟数据供开发测试
        String host = "https://" + bucket + "." + endpoint;
        long expireEndTime = System.currentTimeMillis() + 30 * 1000;
        
        respMap.put("accessid", "mock-access-id-" + UUID.randomUUID().toString().substring(0, 8));
        respMap.put("policy", "mock-policy");
        respMap.put("signature", "mock-signature");
        respMap.put("dir", dir);
        respMap.put("host", host);
        respMap.put("expire", String.valueOf(expireEndTime / 1000));

        return R.ok().put("data", respMap);
    }
}




