package com.simplyfly.service;

import com.simplyfly.dto.request.FlightRequest;
import com.simplyfly.dto.response.FlightResponse;
import com.simplyfly.enums.FlightStatus;
import com.simplyfly.exception.ResourceNotFoundException;
import com.simplyfly.mapper.FlightMapper;
import com.simplyfly.model.Airport;
import com.simplyfly.model.Flight;
import com.simplyfly.model.User;
import com.simplyfly.repository.AirportRepository;
import com.simplyfly.repository.FlightRepository;
import com.simplyfly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.simplyfly.dto.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
public class FlightService{

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;
    private final UserRepository userRepository;
    private final FlightMapper flightMapper;

    public FlightResponse addFlight(FlightRequest request){
        validateFlightRequest(request);

        if(flightRepository.existsByFlightNumber(request.flightNumber())){
            throw new RuntimeException("Flight number already exists: "+request.flightNumber());
        }

        Airport source=airportRepository.findById(request.sourceAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Source airport not found"));

        Airport destination=airportRepository.findById(request.destinationAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination airport not found"));

        User owner=userRepository.findById(request.ownerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        Flight flight=flightMapper.mapToEntity(request,source,destination,owner);
        Flight savedFlight=flightRepository.save(flight);

        return flightMapper.mapToResponse(savedFlight);
    }

    public FlightResponse getFlightById(Long id){
        Flight flight=flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        return flightMapper.mapToResponse(flight);
    }

    public PageResponse<FlightResponse> getAllFlights(
            int page, int size) {

        Pageable pageable = PageRequest.of(
                page, size,
                Sort.by("departureTime").ascending());

        Page<Flight> flightPage = flightRepository.findAll(pageable);

        List<FlightResponse> content = flightPage.getContent()
                .stream()
                .map(flightMapper::mapToResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                content,
                flightPage.getNumber(),
                flightPage.getSize(),
                flightPage.getTotalElements(),
                flightPage.getTotalPages(),
                flightPage.isLast()
        );
    }


    public FlightResponse updateFlight(Long id,FlightRequest request){
        validateFlightRequest(request);

        Flight flight=flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        Airport source=airportRepository.findById(request.sourceAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Source airport not found"));

        Airport destination=airportRepository.findById(request.destinationAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination airport not found"));

        User owner=userRepository.findById(request.ownerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        flight.setFlightName(request.flightName());
        flight.setFlightNumber(request.flightNumber());
        flight.setSourceAirport(source);
        flight.setDestinationAirport(destination);
        flight.setDepartureTime(request.departureTime());
        flight.setArrivalTime(request.arrivalTime());
        flight.setTotalSeats(request.totalSeats());
        flight.setAvailableSeats(request.totalSeats());
        flight.setTicketPrice(request.ticketPrice());
        flight.setCheckinBaggageLimit(request.checkInBaggageLimit());
        flight.setCabinBaggageLimit(request.cabinBaggageLimit());
        flight.setOwner(owner);

        Flight updatedFlight=flightRepository.save(flight);

        return flightMapper.mapToResponse(updatedFlight);
    }

    public void deleteFlight(Long id){
        Flight flight=flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        flightRepository.delete(flight);
    }

    public List<FlightResponse> searchFlights(Long sourceId,Long destinationId,LocalDateTime date){
        List<Flight> flights=flightRepository.searchFlights(sourceId,destinationId,date);

        if(flights.isEmpty()){
            throw new ResourceNotFoundException("No flights found for this route and date");
        }

        return flights.stream()
                .map(flightMapper::mapToResponse)
                .toList();
    }

    public List<FlightResponse> getFlightsByOwner(Long ownerId){
        return flightRepository.findByOwnerId(ownerId)
                .stream()
                .map(flightMapper::mapToResponse)
                .toList();
    }

    public FlightResponse updateFlightStatus(Long id,FlightStatus status){
        Flight flight=flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        flight.setFlightStatus(status);

        return flightMapper.mapToResponse(flightRepository.save(flight));
    }

    private void validateFlightRequest(FlightRequest request){
        if(request.sourceAirportId().equals(request.destinationAirportId())){
            throw new RuntimeException("Source and destination cannot be same");
        }

        if(request.departureTime().isAfter(request.arrivalTime())){
            throw new RuntimeException("Departure time must be before arrival time");
        }
    }
}