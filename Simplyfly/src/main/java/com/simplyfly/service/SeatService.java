package com.simplyfly.service;

import com.simplyfly.dto.request.SeatRequest;
import com.simplyfly.dto.response.SeatResponse;
import com.simplyfly.enums.SeatStatus;
import com.simplyfly.exception.ResourceNotFoundException;
import com.simplyfly.mapper.SeatMapper;
import com.simplyfly.model.Flight;
import com.simplyfly.model.Seat;
import com.simplyfly.repository.FlightRepository;
import com.simplyfly.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService{

    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;
    private final SeatMapper seatMapper;

    public SeatResponse addSeat(SeatRequest request){
        Flight flight=flightRepository.findById(request.flightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        boolean exists=seatRepository.findByFlightId(request.flightId())
                .stream()
                .anyMatch(s -> s.getSeatNumber().equalsIgnoreCase(request.seatNumber()));

        if(exists){
            throw new RuntimeException("Seat number "+request.seatNumber()+" already exists for this flight");
        }

        Seat seat=seatMapper.mapToEntity(request,flight);
        return seatMapper.mapToResponse(seatRepository.save(seat));
    }

    public List<SeatResponse> addSeatsForFlight(Long flightId,int totalSeats){
        Flight flight=flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        if(!seatRepository.findByFlightId(flightId).isEmpty()){
            throw new RuntimeException("Seats already exist for flight id: "+flightId);
        }

        List<Seat> seats=new ArrayList<>();
        String[] cols={"A","B","C","D","E","F"};
        int row=1;
        int col=0;

        for(int i=0;i<totalSeats;i++){
            Seat seat=new Seat();
            seat.setFlight(flight);
            seat.setSeatNumber(row+cols[col]);
            seat.setSeatStatus(SeatStatus.AVAILABLE);
            seats.add(seat);

            col++;
            if(col>=cols.length){
                col=0;
                row++;
            }
        }

        return seatRepository.saveAll(seats)
                .stream()
                .map(seatMapper::mapToResponse)
                .toList();
    }

    public List<SeatResponse> getAllSeats(){
        return seatRepository.findAll()
                .stream()
                .map(seatMapper::mapToResponse)
                .toList();
    }

    public SeatResponse getSeatById(Long id){
        Seat seat=seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

        return seatMapper.mapToResponse(seat);
    }

    public List<SeatResponse> getSeatsByFlight(Long flightId){
        flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        List<Seat> seats=seatRepository.findByFlightId(flightId);

        if(seats.isEmpty()){
            throw new ResourceNotFoundException("No seats found for flight id: "+flightId);
        }

        return seats.stream()
                .map(seatMapper::mapToResponse)
                .toList();
    }

    public List<SeatResponse> getAvailableSeatsByFlight(Long flightId){
        flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        List<Seat> seats=seatRepository.findByFlightIdAndSeatStatus(
                flightId,
                SeatStatus.AVAILABLE
        );

        if(seats.isEmpty()){
            throw new ResourceNotFoundException("No available seats for flight id: "+flightId);
        }

        return seats.stream()
                .map(seatMapper::mapToResponse)
                .toList();
    }

    public List<SeatResponse> getBookedSeatsByFlight(Long flightId){
        flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        List<Seat> seats=seatRepository.findByFlightIdAndSeatStatus(
                flightId,
                SeatStatus.BOOKED
        );

        if(seats.isEmpty()){
            throw new ResourceNotFoundException("No booked seats for flight id: "+flightId);
        }

        return seats.stream()
                .map(seatMapper::mapToResponse)
                .toList();
    }

    public SeatResponse updateSeatStatus(Long id,String status){
        Seat seat=seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

        try{
            SeatStatus seatStatus=SeatStatus.valueOf(status.toUpperCase());
            seat.setSeatStatus(seatStatus);
        }catch(IllegalArgumentException e){
            throw new RuntimeException("Invalid seat status: "+status+". Valid values: AVAILABLE, BOOKED");
        }

        return seatMapper.mapToResponse(seatRepository.save(seat));
    }

    public void deleteSeat(Long id){
        Seat seat=seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

        if(seat.getSeatStatus()==SeatStatus.BOOKED){
            throw new RuntimeException("Cannot delete a booked seat");
        }

        seatRepository.delete(seat);
    }
}