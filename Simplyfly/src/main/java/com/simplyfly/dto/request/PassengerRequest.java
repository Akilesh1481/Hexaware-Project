package com.simplyfly.dto.request;

import jakarta.validation.constraints.*;


public record PassengerRequest(
        @NotNull(message = "Booking ID is required")
        Long bookingId,

        @NotBlank(message = "Passenger name is required")
        String passengerName,

        @NotNull(message = "Age is required")
        Integer age,

        @NotBlank(message = "Gender is required")
        String gender,

        @NotNull(message = "Seat ID is required")
        Long seatId,

        String passportNumber,

        @Email(message = "Invalid email format")
        String email
)  {


}