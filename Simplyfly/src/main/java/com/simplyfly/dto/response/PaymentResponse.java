package com.simplyfly.dto.response;

import com.simplyfly.enums.PaymentMethod;
import com.simplyfly.enums.PaymentStatus;

import java.time.LocalDateTime;

public record PaymentResponse(
        Long id,
        Long bookingId,
        Double amount,
        PaymentStatus paymentStatus,
        PaymentMethod paymentMethod,
        LocalDateTime paymentDate
) {
}
