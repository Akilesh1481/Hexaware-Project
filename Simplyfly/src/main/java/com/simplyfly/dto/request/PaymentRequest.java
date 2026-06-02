package com.simplyfly.dto.request;

import com.simplyfly.enums.PaymentMethod;
import com.simplyfly.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PaymentRequest(
        @NotNull(message = "Booking ID is required")
         Long bookingId,

        @NotNull(message = "Amount is required")
         Double amount,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod
) {
}
