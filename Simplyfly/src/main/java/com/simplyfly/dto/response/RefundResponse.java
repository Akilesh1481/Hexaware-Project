package com.simplyfly.dto.response;

import com.simplyfly.enums.RefundStatus;
import java.time.LocalDateTime;

public record RefundResponse(
        Long id,
        Long bookingId,
        Double amount,
        RefundStatus refundStatus,
        LocalDateTime refundDate
){
}