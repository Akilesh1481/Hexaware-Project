package com.simplyfly.repository;

import com.simplyfly.model.Refund;
import com.simplyfly.enums.RefundStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefundRepository extends JpaRepository<Refund,Long> {
    Optional<Refund> findByBookingId(Long bookingId);
    List<Refund> findByRefundStatus(RefundStatus status);
}
