package com.noir.job.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResumeParseRequest {

    @NotBlank(message = "Resume text is required")
    private String resumeText;
}
