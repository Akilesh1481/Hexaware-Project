package com.simplyfly.dto.response;

public record AirportResponse (
        Long id,
        String airportName,
        String city,
        String country,
        String airportCode
){

}