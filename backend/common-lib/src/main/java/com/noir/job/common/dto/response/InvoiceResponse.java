package com.noir.job.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.noir.job.common.domain.PaymentPurpose;
import com.noir.job.common.domain.SubscriptionPlan;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceResponse {

    private Long id;
    private String invoiceNumber;
    private String transactionNumber;

    private Long companyId;

    // Amount breakdown
    private BigDecimal subTotal;
    private BigDecimal taxAmount;
    private BigDecimal taxRate;
    private BigDecimal totalAmount;
    private String currency;

    // What was purchased
    private PaymentPurpose purpose;
    private SubscriptionPlan subscriptionPlan;
    private String description;
    private LocalDate billingPeriodStart;
    private LocalDate billingPeriodEnd;

    // Billing snapshot
    private String companyName;
    private String billingEmail;
    private String billingAddress;
    private String taxId;

    private LocalDate invoiceDate;
    private LocalDateTime createdAt;
}
