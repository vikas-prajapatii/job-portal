package com.noir.job.event;

import com.noir.job.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationKafkaConsumer {

    private final EmailNotificationService emailService;
    @KafkaListener(
            topics = "application.status.changed",
            groupId = "notification-service",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleStatusChanged(ApplicationStatusChangedEvent event) throws Exception {
//        log.info("Received status-changed event: application={}, newStatus={}",
//                event.getApplicationId(), event.getNewStatus());
        System.out.println("recieved status changed");
        emailService.sendStatusChangedEmail(event);
    }
}
