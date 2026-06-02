package com.simplyfly.repository;

import com.simplyfly.model.Booking;
import com.simplyfly.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByFlightId(Long flightId);
    List<Booking> findByBookingStatus(BookingStatus status);
    List<Booking> findByUserIdAndBookingStatus(Long userId, BookingStatus status);
}
