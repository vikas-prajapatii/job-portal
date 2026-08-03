package com.noir.job.payload;

import com.noir.job.domain.ResumeTemplate;
import com.noir.job.domain.ResumeVisibility;
import com.noir.job.dto.ResumeResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateResumeRequest {
    @NotBlank(message = "Resume title is required")
    private String title;
    private ResumeVisibility visibility;
    private ResumeTemplate template;
    private ResumeResponse resume;
    private Boolean isDefault = false;

}
