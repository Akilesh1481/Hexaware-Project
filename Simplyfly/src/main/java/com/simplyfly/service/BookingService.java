package com.simplyfly.service;

import com.simplyfly.dto.request.BookingRequest;
import com.simplyfly.dto.response.BookingResponse;
import com.simplyfly.enums.BookingStatus;
import com.simplyfly.enums.SeatStatus;
import com.simplyfly.exception.ResourceNotFoundException;
import com.simplyfly.mapper.BookingMapper;
import com.simplyfly.model.Booking;
import com.simplyfly.model.Flight;
import com.simplyfly.model.Seat;
import com.simplyfly.model.User;
import com.simplyfly.repository.BookingRepository;
import com.simplyfly.repository.FlightRepository;
import com.simplyfly.repository.SeatRepository;
import com.simplyfly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;
    private final BookingMapper bookingMapper;

    public BookingResponse createBooking(BookingRequest request){

        User user=userRepository.findById(request.userId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Flight flight=flightRepository.findById(request.flightId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Flight not found"));

        if(flight.getAvailableSeats()<request.noOfSeats()){
            throw new RuntimeException("Not enough seats available");
        }

        Booking booking=
                bookingMapper.mapToEntity(request,user,flight);

        Booking savedBooking=
                bookingRepository.save(booking);

        request.seatIds().forEach(seatId -> {

            Seat seat=seatRepository.findById(seatId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Seat not found"));

            seat.setSeatStatus(SeatStatus.BOOKED);

            seatRepository.save(seat);
        });

        flight.setAvailableSeats(
                flight.getAvailableSeats()-request.noOfSeats()
        );

        flightRepository.save(flight);

        return bookingMapper.mapToResponse(savedBooking);
    }

    public BookingResponse getBookingById(Long id){

        Booking booking=bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found"));

        return bookingMapper.mapToResponse(booking);
    }

    public List<BookingResponse> getAllBookings(){

        return bookingRepository.findAll()
                .stream()
                .map(bookingMapper::mapToResponse)
                .toList();
    }

    public List<BookingResponse> getBookingsByUser(Long userId){

        return bookingRepository.findByUserId(userId)
                .stream()
                .map(bookingMapper::mapToResponse)
                .toList();
    }

    public List<BookingResponse> getBookingsByFlight(Long flightId){

        return bookingRepository.findByFlightId(flightId)
                .stream()
                .map(bookingMapper::mapToResponse)
                .toList();
    }

    public BookingResponse cancelBooking(Long id){

        Booking booking=bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found"));

        if(booking.getBookingStatus()==BookingStatus.CANCELLED){
            throw new RuntimeException(
                    "Booking already cancelled");
        }

        booking.setBookingStatus(
                BookingStatus.CANCELLED
        );

        Flight flight=booking.getFlight();

        flight.setAvailableSeats(
                flight.getAvailableSeats()
                        + booking.getNoOfSeats()
        );

        flightRepository.save(flight);

        Booking updatedBooking = bookingRepository.save(booking);

        return bookingMapper.mapToResponse(updatedBooking);
    }
}