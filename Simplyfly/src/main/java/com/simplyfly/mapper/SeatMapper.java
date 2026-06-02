package com.simplyfly.mapper;

import com.simplyfly.dto.request.SeatRequest;
import com.simplyfly.dto.response.SeatResponse;
import com.simplyfly.enums.SeatStatus;
import com.simplyfly.model.Flight;
import com.simplyfly.model.Seat;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {

    public Seat mapToEntity(SeatRequest dto, Flight flight) {

        Seat seat = new Seat();

        seat.setFlight(flight);
        seat.setSeatNumber(dto.seatNumber());

        if(dto.seatStatus() == null){
            seat.setSeatStatus(SeatStatus.AVAILABLE);
        }else{
            seat.setSeatStatus(dto.seatStatus());
        }

        return seat;
    }

    public SeatResponse mapToResponse(Seat seat) {

        return new SeatResponse(
                seat.getId(),
                seat.getFlight().getId(),
                seat.getFlight().getFlightNumber(),
                seat.getSeatNumber(),
                seat.getSeatStatus()
        );
    }
}