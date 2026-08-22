package com.noir.job.controller;

import com.noir.job.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {
    private final EmailNotificationService emailNotificationService;
    @GetMapping("/sent")
    public String notificationController() throws Exception {
        return  "email sent successfully";
    }
}
