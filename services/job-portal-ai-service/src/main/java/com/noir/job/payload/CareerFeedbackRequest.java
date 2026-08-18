package com.noir.job.payload;

import lombok.Data;

@Data
public class CareerFeedbackRequest {
    private String targetJobTitle;
    private String resumeContent;
}
