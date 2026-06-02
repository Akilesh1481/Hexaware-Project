package com.simplyfly.dto.response;

import com.simplyfly.enums.SeatStatus;

public record SeatResponse(

        Long id,
        Long flightId,
        String flightNumber,
        String seatNumber,
        SeatStatus seatStatus

){
}