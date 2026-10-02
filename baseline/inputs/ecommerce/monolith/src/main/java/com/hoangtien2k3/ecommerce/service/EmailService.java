package com.hoangtien2k3.ecommerce.service;

import com.hoangtien2k3.ecommerce.dto.EmailDetails;
import org.springframework.web.multipart.MultipartFile;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);
    String sendMailWithAttachment(EmailDetails details);
    String sendMail(MultipartFile[] file, String to, String[] cc, String subject, String body);
}
