package com.bsuir.service.impl;

import com.bsuir.entity.EmailDetails;
import com.bsuir.service.NotificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String emailSender;

    @Override
    public void sendEmail(EmailDetails emailDetails) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.setTo(emailDetails.getRecipient());
            helper.setFrom(emailSender);
            helper.setText(emailDetails.getMessageBody(), true);
            helper.setSubject(emailDetails.getSubject());
            javaMailSender.send(message);
        } catch (MailException | MessagingException e) {
            e.printStackTrace();
        }
    }
}