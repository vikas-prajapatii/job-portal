package com.noir.job.payload;

import com.noir.job.domain.SkillCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobSkillRequest {
    @NotBlank(message = "skill name is required")
    @Size(max = 100, message = "name must not exceed than 100 characters")
    private String name;
    @NotNull(message = "skill category is required")
    private SkillCategory category;
}
