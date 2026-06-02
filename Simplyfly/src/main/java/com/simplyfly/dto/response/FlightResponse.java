package com.simplyfly.dto.response;


import com.simplyfly.enums.FlightStatus;
import java.time.LocalDateTime;

public record FlightResponse( 
     Long id,
     String flightName,
     String flightNumber,
     Long sourceAirportId,
     String sourceAirportName,
     String sourceCity,
     Long destinationAirportId,
     String destinationAirportName,
     String destinationCity,
     LocalDateTime departureTime,
     LocalDateTime arrivalTime,
     Integer totalSeats,
     Integer availableSeats,
     Double ticketPrice,
     Integer checkinBaggageLimit,
     Integer cabinBaggageLimit,
     FlightStatus flightStatus,
     Long ownerId,
     String ownerName
){
}