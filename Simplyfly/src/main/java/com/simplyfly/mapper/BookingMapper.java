package com.simplyfly.mapper;

import com.simplyfly.dto.request.BookingRequest;
import com.simplyfly.dto.response.BookingResponse;
import com.simplyfly.enums.BookingStatus;
import com.simplyfly.model.Booking;
import com.simplyfly.model.Flight;
import com.simplyfly.model.User;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public Booking mapToEntity(BookingRequest dto,
                               User user,
                               Flight flight){

        Booking booking=new Booking();
        booking.setUser(user);
        booking.setFlight(flight);
        booking.setNoOfSeats(dto.noOfSeats());
        booking.setTotalPrice(
                flight.getTicketPrice()*dto.noOfSeats()
        );
        booking.setBookingStatus(BookingStatus.BOOKED);

        return booking;
    }

    public BookingResponse mapToResponse(Booking b){

        return new BookingResponse(
                b.getId(),
                b.getUser().getId(),
                b.getUser().getFullName(),
                b.getFlight().getId(),
                b.getFlight().getFlightNumber(),
                b.getFlight().getSourceAirport().getCity(),
                b.getFlight().getDestinationAirport().getCity(),
                b.getBookingDate(),
                b.getNoOfSeats(),
                b.getTotalPrice(),
                b.getBookingStatus()
        );
    }
}