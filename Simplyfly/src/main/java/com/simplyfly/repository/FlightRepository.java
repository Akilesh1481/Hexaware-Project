package com.simplyfly.repository;

import com.simplyfly.model.Flight;
import com.simplyfly.enums.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f WHERE f.sourceAirport.id = :sourceId " +
            "AND f.destinationAirport.id = :destId " +
            "AND DATE(f.departureTime) = DATE(:date) " +
            "AND f.flightStatus = 'SCHEDULED'")
    List<Flight> searchFlights(@Param("sourceId") Long sourceId,
                               @Param("destId") Long destId,
                               @Param("date") LocalDateTime date);

    List<Flight> findByOwnerId(Long ownerId);
    List<Flight> findByFlightStatus(FlightStatus status);
    Boolean existsByFlightNumber(String flightNumber);
}