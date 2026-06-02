package com.simplyfly.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BookingRequest(
        @NotNull(message = "User ID is required")
         Long userId,

        @NotNull(message = "Flight ID is required")
         Long flightId,

        @NotNull(message = "Number of seats is required")
        @Min(value = 1, message = "At least 1 seat required")
         Integer noOfSeats,

        @NotEmpty(message = "At least one seat must be selected")
        List<Long> seatIds
) {
}
