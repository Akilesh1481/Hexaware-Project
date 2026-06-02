package com.simplyfly.repository;

import com.simplyfly.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    Optional<Airport> findByAirportCode(String airportCode);
    List<Airport> findByCityContainingIgnoreCase(String city);
    Boolean existsByAirportCode(String airportCode);
}