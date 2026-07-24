package com.noir.job.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobCategoryResponse {
    private Long id;
    private String name;
    private String description;
}
