package com.noir.job.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.noir.job.common.domain.PaymentGateway;
import com.noir.job.common.domain.PaymentMethod;
import com.noir.job.common.domain.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {

    private Long id;
    private String transactionNumber;
    private String orderNumber;

    private Long companyId;

    private BigDecimal amount;
    private String currency;

    private PaymentGateway gateway;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;

    /** Gateway payment ID — useful for support lookups. */
    private String gatewayPaymentId;

    private String failureReason;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;
}
