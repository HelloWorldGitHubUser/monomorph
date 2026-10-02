package com.central.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 验证码控制器
 * 对应微服务 zlt-uaa 的 com.central.oauth.controller.ValidateCodeController
 * 模拟网关路由前缀 /api-uaa
 * @author zlt
 */
@Slf4j
@Controller
@RequestMapping("/api-uaa")
public class ValidateCodeController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // 验证码缓存前缀
    private static final String CODE_PREFIX = "captcha:";

    /**
     * 获取图形验证码
     */
    @GetMapping("/validata/code/{deviceId}")
    public void captcha(@PathVariable String deviceId, HttpServletResponse response) throws IOException {
        // 生成4位随机验证码
        String code = generateCode(4);

        // 缓存验证码 (5分钟过期)
        stringRedisTemplate.opsForValue().set(CODE_PREFIX + deviceId, code, 5, TimeUnit.MINUTES);

        // 生成验证码图片
        BufferedImage image = createImage(code);

        // 输出图片
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache");
        OutputStream out = response.getOutputStream();
        ImageIO.write(image, "png", out);
        out.close();
    }

    /**
     * 生成随机验证码
     */
    private String generateCode(int length) {
        String chars = "ABCDEFGHJKMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }
        return code.toString();
    }

    /**
     * 生成验证码图片
     */
    private BufferedImage createImage(String code) {
        int width = 100;
        int height = 36;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 背景
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 边框
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(0, 0, width - 1, height - 1);

        // 干扰线
        Random random = new Random();
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 5; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }

        // 验证码文字
        g.setFont(new Font("Arial", Font.BOLD, 24));
        for (int i = 0; i < code.length(); i++) {
            g.setColor(new Color(random.nextInt(150), random.nextInt(150), random.nextInt(150)));
            g.drawString(String.valueOf(code.charAt(i)), 15 + i * 20, 28);
        }

        g.dispose();
        return image;
    }
}
