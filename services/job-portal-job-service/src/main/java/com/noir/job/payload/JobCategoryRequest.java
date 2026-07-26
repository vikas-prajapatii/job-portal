package com.noir.job.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobCategoryRequest {
    @NotBlank(message = "category name is required")
    private String name;
    @Size(max = 500, message = "Description must not exceed more than 500 words")
    private String description;

    private String iconUrl;

    private Long parentId;

}
