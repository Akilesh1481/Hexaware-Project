package com.simplyfly.repository;

import com.simplyfly.model.Seat;
import com.simplyfly.enums.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {
    List<Seat> findByFlightId(Long flightId);
    List<Seat> findByFlightIdAndSeatStatus(Long flightId, SeatStatus status);
    long countByFlightIdAndSeatStatus(Long flightId, SeatStatus status);
}
