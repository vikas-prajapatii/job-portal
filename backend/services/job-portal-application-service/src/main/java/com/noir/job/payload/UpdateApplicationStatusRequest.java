package com.noir.job.payload;

import com.noir.job.domain.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateApplicationStatusRequest {
    @NotNull(message = "New status is required")
    private ApplicationStatus status;
    private String note;
}
