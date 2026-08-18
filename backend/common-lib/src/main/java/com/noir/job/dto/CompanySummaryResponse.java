package com.noir.job.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanySummaryResponse {
    private Long id;
    private String name;
    private String logoUrl;
    private String website;
}
