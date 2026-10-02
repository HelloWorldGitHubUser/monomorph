package com.hoangtien2k3.ecommerce.controller;

import com.hoangtien2k3.ecommerce.dto.EmailDetails;
import com.hoangtien2k3.ecommerce.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/sendSimpleMail")
    public ResponseEntity<String> getEmailRouteStatus() {
        return ResponseEntity.ok("Email service route is available");
    }

    @PostMapping("/sendSimpleMail")
    public ResponseEntity<String> sendSimpleMail(@RequestBody EmailDetails details) {
        return ResponseEntity.ok(emailService.sendSimpleMail(details));
    }

    @PostMapping("/sendMailWithAttachment")
    public ResponseEntity<String> sendMailWithAttachment(@RequestBody EmailDetails details) {
        return ResponseEntity.ok(emailService.sendMailWithAttachment(details));
    }

    @PostMapping("/sendMail")
    public ResponseEntity<String> sendMail(@RequestParam(value = "file", required = false) MultipartFile[] files,
                                           @RequestParam String to,
                                           @RequestParam String[] cc,
                                           @RequestParam String subject,
                                           @RequestParam String body) {
        return ResponseEntity.ok(emailService.sendMail(files, to, cc, subject, body));
    }

}
