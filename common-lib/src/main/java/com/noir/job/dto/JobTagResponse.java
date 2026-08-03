package com.noir.job.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class JobTagResponse {
    private Long id;
    private String name;
    private String slug;
}
