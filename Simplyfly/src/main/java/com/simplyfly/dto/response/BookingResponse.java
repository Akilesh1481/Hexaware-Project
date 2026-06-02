package com.simplyfly.dto.response;

import com.simplyfly.enums.BookingStatus;

import java.time.LocalDateTime;

public record BookingResponse(
         Long id,
         Long userId,
         String userName,
         Long flightId,
         String flightNumber,
         String sourceCity,
         String destinationCity,
         LocalDateTime bookingDate,
         Integer noOfSeats,
         Double totalPrice,
         BookingStatus bookingStatus) {
}
