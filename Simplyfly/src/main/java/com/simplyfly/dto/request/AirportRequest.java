package com.simplyfly.dto.request;

import jakarta.validation.constraints.*;



public record AirportRequest (

    @NotBlank(message = "Airport name is required")
    String airportName,

    @NotBlank(message = "City is required")
    String city,

    @NotBlank(message = "Country is required")
    String country,

    @NotBlank(message = "Airport code is required")
    String airportCode
    ){
}