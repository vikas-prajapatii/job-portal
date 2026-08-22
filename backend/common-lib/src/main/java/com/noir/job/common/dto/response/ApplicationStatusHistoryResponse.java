package com.noir.job.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.noir.job.common.domain.ApplicationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationStatusHistoryResponse {

    private Long id;
    private ApplicationStatus fromStatus;
    private ApplicationStatus toStatus;
    private Long changedByUserId;
    private String note;
    private LocalDateTime changedAt;
}
