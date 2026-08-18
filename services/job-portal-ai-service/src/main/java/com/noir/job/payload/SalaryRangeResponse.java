package com.noir.job.payload;

import lombok.Data;

@Data
public class SalaryRangeResponse {
    private double minSalary;
    private double maxSalary;
    private String currency;
    private String period;
    private String marketInsight;
}
