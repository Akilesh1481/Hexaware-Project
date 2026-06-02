package com.simplyfly.service;

import com.simplyfly.dto.request.PassengerRequest;
import com.simplyfly.dto.response.PassengerResponse;
import com.simplyfly.exception.ResourceNotFoundException;
import com.simplyfly.mapper.PassengerMapper;
import com.simplyfly.model.Booking;
import com.simplyfly.model.Passenger;
import com.simplyfly.model.Seat;
import com.simplyfly.repository.BookingRepository;
import com.simplyfly.repository.PassengerRepository;
import com.simplyfly.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerService{

    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final PassengerMapper passengerMapper;

    public PassengerResponse addPassenger(PassengerRequest request){
        Booking booking=bookingRepository.findById(request.bookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking unavailable"));

        Seat seat=seatRepository.findById(request.seatId())
                .orElseThrow(() -> new ResourceNotFoundException("Seat unavailable"));

        if(request.passportNumber()!=null && !request.passportNumber().isBlank()
                && passengerRepository.existsByPassportNumber(request.passportNumber())){
            throw new RuntimeException("Passport number already used: "+request.passportNumber());
        }

        Passenger passenger=passengerMapper.mapToEntity(request,booking,seat);

        Passenger savedPassenger=passengerRepository.save(passenger);

        return passengerMapper.mapToResponse(savedPassenger);
    }

    public List<PassengerResponse> getPassengersByBooking(Long bookingId){
        return passengerRepository.findByBookingId(bookingId)
                .stream()
                .map(passengerMapper::mapToResponse)
                .toList();
    }

    public void deletePassenger(Long id){
        Passenger passenger=passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not available"));

        passengerRepository.delete(passenger);
    }
}