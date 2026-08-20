package com.noir.job.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class EmailNotificationService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendStatusChangedEmail() throws Exception {
        try{
            String subject = "application updated";
            String body = buildStatusChangeHtml();
            String candidateEmail = "vikasprajapati6742@gmail.com";
            sendEmail(candidateEmail,subject,body);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    private void sendEmail(String candidateEmail, String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true,"UTF-8");
        mimeMessageHelper.setFrom(fromEmail);
        mimeMessageHelper.setTo(candidateEmail);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body,true);
        mailSender.send(mimeMessage);

    }
    private String buildStatusChangeHtml(){
        return "<h1>Application has been updated</h1>";
    }


}
