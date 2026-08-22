package com.noir.job.common.domain;

public enum RefundStatus {
    PENDING,     // refund request raised
    PROCESSING,  // sent to gateway
    SUCCESS,     // credited back to customer
    FAILED       // gateway rejected the refund
}
