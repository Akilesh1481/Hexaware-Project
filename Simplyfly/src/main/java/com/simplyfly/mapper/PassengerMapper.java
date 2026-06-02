package com.simplyfly.mapper;

import com.simplyfly.dto.request.PassengerRequest;
import com.simplyfly.dto.response.PassengerResponse;
import com.simplyfly.model.Booking;
import com.simplyfly.model.Passenger;
import com.simplyfly.model.Seat;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper{

    public Passenger mapToEntity(PassengerRequest dto,Booking booking,Seat seat){
        Passenger passenger=new Passenger();
        passenger.setBooking(booking);
        passenger.setPassengerName(dto.passengerName());
        passenger.setAge(dto.age());
        passenger.setGender(dto.gender());
        passenger.setSeat(seat);
        passenger.setPassportNumber(dto.passportNumber());
        passenger.setEmail(dto.email());
        return passenger;
    }

    public PassengerResponse mapToResponse(Passenger p){
        return new PassengerResponse(
                p.getId(),
                p.getBooking().getId(),
                p.getPassengerName(),
                p.getAge(),
                p.getGender(),
                p.getSeat().getSeatNumber(),
                p.getPassportNumber(),
                p.getEmail()
        );
    }
}