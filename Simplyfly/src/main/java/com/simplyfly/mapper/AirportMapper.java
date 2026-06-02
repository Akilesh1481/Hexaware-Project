package com.simplyfly.mapper;

import com.simplyfly.dto.request.AirportRequest;
import com.simplyfly.dto.response.AirportResponse;
import com.simplyfly.model.Airport;
import org.springframework.stereotype.Component;

@Component
public class AirportMapper{

    public Airport mapToEntity(AirportRequest dto){
        Airport airport=new Airport();
        airport.setAirportName(dto.airportName());
        airport.setCity(dto.city());
        airport.setCountry(dto.country());
        airport.setAirportCode(dto.airportCode().toUpperCase());
        return airport;
    }

    public AirportResponse mapToResponse(Airport airport){
        return new AirportResponse(
                airport.getId(),
                airport.getAirportName(),
                airport.getCity(),
                airport.getCountry(),
                airport.getAirportCode()
        );
    }
}