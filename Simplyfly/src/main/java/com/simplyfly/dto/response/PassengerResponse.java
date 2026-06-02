package com.simplyfly.dto.response;


public record PassengerResponse(
        Long id,
        Long bookingId,
        String passengerName,
        Integer age,
        String gender,
        String seatNumber,
        String passportNumber,
        String email
)  {

}