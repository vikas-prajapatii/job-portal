package com.noir.job.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalaryRangeResponse {

    private Long minSalary;
    private Long maxSalary;
    private String currency;
    private String period;
    private String marketInsight;
}
