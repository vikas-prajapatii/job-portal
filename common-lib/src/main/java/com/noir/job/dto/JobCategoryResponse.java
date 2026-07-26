package com.noir.job.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class JobCategoryResponse {
    private Long id;
    private String name;
    private String description;
    private String slug;
    private String iconUrl;
    private boolean active;
    private Long parentId;
    private String parentName;
    private List<JobCategoryResponse> subCategories;
    private LocalDateTime createdAt;
}
