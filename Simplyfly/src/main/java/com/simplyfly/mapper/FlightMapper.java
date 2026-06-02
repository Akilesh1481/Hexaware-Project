package com.simplyfly.mapper;

import com.simplyfly.dto.request.FlightRequest;
import com.simplyfly.dto.response.FlightResponse;
import com.simplyfly.enums.FlightStatus;
import com.simplyfly.model.Airport;
import com.simplyfly.model.Flight;
import com.simplyfly.model.User;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper{

    public Flight mapToEntity(FlightRequest dto,Airport source,Airport destination,User owner){
        Flight flight=new Flight();
        flight.setFlightName(dto.flightName());
        flight.setFlightNumber(dto.flightNumber());
        flight.setSourceAirport(source);
        flight.setDestinationAirport(destination);
        flight.setDepartureTime(dto.departureTime());
        flight.setArrivalTime(dto.arrivalTime());
        flight.setTotalSeats(dto.totalSeats());
        flight.setAvailableSeats(dto.totalSeats());
        flight.setTicketPrice(dto.ticketPrice());
        flight.setCheckinBaggageLimit(dto.checkInBaggageLimit());
        flight.setCabinBaggageLimit(dto.cabinBaggageLimit());
        flight.setFlightStatus(FlightStatus.SCHEDULED);
        flight.setOwner(owner);
        return flight;
    }

    public FlightResponse mapToResponse(Flight f){
        return new FlightResponse(
                f.getId(),
                f.getFlightName(),
                f.getFlightNumber(),
                f.getSourceAirport().getId(),
                f.getSourceAirport().getAirportName(),
                f.getSourceAirport().getCity(),
                f.getDestinationAirport().getId(),
                f.getDestinationAirport().getAirportName(),
                f.getDestinationAirport().getCity(),
                f.getDepartureTime(),
                f.getArrivalTime(),
                f.getTotalSeats(),
                f.getAvailableSeats(),
                f.getTicketPrice(),
                f.getCheckinBaggageLimit(),
                f.getCabinBaggageLimit(),
                f.getFlightStatus(),
                f.getOwner().getId(),
                f.getOwner().getFullName()
        );
    }
}