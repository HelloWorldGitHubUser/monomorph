package com.hoangtien2k3.ecommerce.listener;

import com.hoangtien2k3.ecommerce.dto.EmailDetails;
import com.hoangtien2k3.ecommerce.dto.PaymentEventDto;
import com.hoangtien2k3.ecommerce.dto.PaymentRecordDto;
import com.hoangtien2k3.ecommerce.event.PaymentSuccessEvent;
import com.hoangtien2k3.ecommerce.event.UserProfileEvent;
import com.hoangtien2k3.ecommerce.model.notification.PaymentRecordStatus;
import com.hoangtien2k3.ecommerce.service.EmailService;
import com.hoangtien2k3.ecommerce.service.PaymentRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final EmailService emailService;
    private final PaymentRecordService paymentRecordService;

    @Async
    @EventListener
    public void onUserProfile(UserProfileEvent event) {
        log.info("Received user profile event, sending email...");
        String result = emailService.sendSimpleMail(event.getEmailDetails());
        log.info("send email result -> user-service change password: {}", result);
    }

    @Async
    @EventListener
    public void onPaymentSuccess(PaymentSuccessEvent event) {
        log.info("Received payment success event, saving record and sending email...");
        PaymentEventDto paymentInfo = event.getPaymentInfo();

        PaymentRecordDto paymentRecordDto = PaymentRecordDto.builder()
                .paymentId(paymentInfo.getPaymentId())
                .isPayed(paymentInfo.getIsPayed())
                .paymentStatus(PaymentRecordStatus.valueOf(paymentInfo.getPaymentStatus().name()))
                .orderId(paymentInfo.getOrderId())
                .userId(paymentInfo.getUserId())
                .build();
        paymentRecordService.savePayment(paymentRecordDto);

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient("hoangtien2k3dev@gmail.com")
                .msgBody(buildPaymentMsgBody(paymentInfo.getIsPayed(), paymentInfo.getPaymentStatus().getStatus()))
                .subject("Payment Successfully in Order with userId: " + paymentInfo.getUserId())
                .attachment("Please, check the full information in invoice: " + LocalDateTime.now())
                .build();
        String result = emailService.sendSimpleMail(emailDetails);
        log.info("Payment notification email sent for userId: {}, result: {}", paymentInfo.getUserId(), result);
    }

    private String buildPaymentMsgBody(Boolean isPayed, String paymentStatusValue) {
        return "Payment in order product cart successfully: \n " +
                " + IsPays: " + isPayed +
                "\n + PaymentStatus: " + paymentStatusValue +
                "\n\nDate: " + LocalDate.now() +
                "\nTime: " + LocalTime.now();
    }

}
