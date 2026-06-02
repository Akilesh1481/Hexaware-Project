package com.simplyfly.dto.request;


import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;


public record FlightRequest(
        @NotBlank(message = "Flight name is required")
         String flightName,

        @NotBlank(message = "Flight number is required")
         String flightNumber,

        @NotNull(message = "Source airport is required")
         Long sourceAirportId,

        @NotNull(message = "Destination airport is required")
         Long destinationAirportId,

        @NotNull(message = "Departure time is required")
         LocalDateTime departureTime,

        @NotNull(message = "Arrival time is required")
         LocalDateTime arrivalTime,

        @NotNull(message = "Total seats is required")
        @Min(value = 1, message = "At least 1 seat required")
         Integer totalSeats,

        @NotNull(message = "Ticket price is required")
        @Min(value = 0, message = "Price cannot be negative")
         Double ticketPrice,

         Integer checkInBaggageLimit,
         Integer cabinBaggageLimit,

        @NotNull(message = "Owner ID is required")
         Long ownerId)  {


}