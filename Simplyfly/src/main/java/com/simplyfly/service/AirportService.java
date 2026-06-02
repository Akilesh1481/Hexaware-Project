package com.simplyfly.service;

import com.simplyfly.dto.request.AirportRequest;
import com.simplyfly.dto.response.AirportResponse;
import com.simplyfly.dto.response.PageResponse;
import com.simplyfly.exception.ResourceNotFoundException;
import com.simplyfly.mapper.AirportMapper;
import com.simplyfly.model.Airport;
import com.simplyfly.repository.AirportRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;


    public AirportResponse addAirport(AirportRequest request) {
        if (airportRepository.existsByAirportCode(
                request.airportCode())) {
            throw new RuntimeException(
                    "Airport code already exists: "
                            + request.airportCode());
        }
        Airport airport = airportMapper.mapToEntity(request);
        return airportMapper.mapToResponse(
                airportRepository.save(airport));
    }


    public AirportResponse getAirportById(Long id) {
        return airportMapper.mapToResponse(
                airportRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Airport not found")));
    }

    public PageResponse<AirportResponse> getAllAirports(
            int page, int size) {

        Pageable pageable = PageRequest.of(
                page, size,
                Sort.by("airportName").ascending());

        Page<Airport> airportPage = airportRepository.findAll(pageable);

        List<AirportResponse> content = airportPage.getContent()
                .stream()
                .map(airportMapper::mapToResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                content,
                airportPage.getNumber(),
                airportPage.getSize(),
                airportPage.getTotalElements(),
                airportPage.getTotalPages(),
                airportPage.isLast()
        );
    }

    public AirportResponse updateAirport(Long id,
                                         AirportRequest request) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Airport not found"));
        airport.setAirportName(request.airportName());
        airport.setCity(request.city());
        airport.setCountry(request.country());
        airport.setAirportCode(request.airportCode().toUpperCase());
        return airportMapper.mapToResponse(
                airportRepository.save(airport));
    }

    public void deleteAirport(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Airport mot found"));
        airportRepository.delete(airport);
    }


    public List<AirportResponse> searchByCity(String city) {
        List<Airport> airports = airportRepository
                .findByCityContainingIgnoreCase(city);
        if (airports.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No airports found in city: " + city);
        }
        return airports.stream()
                .map(airportMapper::mapToResponse)
                .collect(Collectors.toList());
    }


    public AirportResponse getAirportByCode(String code) {
        return airportMapper.mapToResponse(
                airportRepository.findByAirportCode(code.toUpperCase())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Airport not found")));
    }
}