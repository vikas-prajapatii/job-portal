package com.noir.job.event;

import com.noir.job.client.CompanyClient;
import com.noir.job.client.JobClient;
import com.noir.job.client.UserClient;
import com.noir.job.domain.ApplicationStatus;
import com.noir.job.dto.CompanyResponse;
import com.noir.job.dto.JobResponse;
import com.noir.job.dto.response.UserResponse;
import com.noir.job.model.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ApplicationEventPublisher {
    public static final String TOPIC = "application.status.changed";
//    public static final String TOPIC_NOTE_ADDED = "application.note.added";
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final UserClient userClient;
    private final JobClient jobClient;
    private final CompanyClient companyClient;
    public void publishStatusChanged(Application app,
                                     ApplicationStatus oldStatus,
                                     String note) {
       try{
           UserResponse candidate = userClient.getUserById(app.getCandidateId());
           JobResponse job = jobClient.getJobById(app.getJobId());
           CompanyResponse company = companyClient.getCompanyById(app.getJobId());
           ApplicationStatusChangedEvent event = ApplicationStatusChangedEvent.builder()
                   .applicationId(app.getId())
                   .candidateId(app.getCandidateId())
                   .candidateEmail(candidate.getEmail())
                   .candidateName(candidate.getFullName())
                   .oldStatus(oldStatus)
                   .newStatus(app.getStatus())
                   .note(note)
                   .jobTitle(job.getTitle())
                   .companyName(company.getName())
                   .changedAt(LocalDateTime.now())
                   .build();

           kafkaTemplate.send(TOPIC,String.valueOf(app.getId()), event);
       } catch (Exception e){
           System.out.println("Error in publish status"+ e.getMessage());
       }


    }
}
