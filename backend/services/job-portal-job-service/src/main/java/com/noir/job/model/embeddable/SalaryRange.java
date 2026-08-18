package com.noir.job.model.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryRange {
    private BigDecimal minSalary;
    private BigDecimal maxSalary;

}
