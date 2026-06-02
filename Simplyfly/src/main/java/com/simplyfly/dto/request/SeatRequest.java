package com.simplyfly.dto.request;

import com.simplyfly.enums.SeatStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SeatRequest(

        @NotNull(message = "Flight ID is required")
        Long flightId,

        @NotBlank(message = "Seat number is required")
        @Size(max = 10, message = "Seat number max 10 characters")
        String seatNumber,

        SeatStatus seatStatus

){
}