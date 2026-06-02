package com.simplyfly.service;

import com.simplyfly.dto.response.RefundResponse;
import com.simplyfly.enums.BookingStatus;
import com.simplyfly.enums.RefundStatus;
import com.simplyfly.exception.ResourceNotFoundException;
import com.simplyfly.model.Booking;
import com.simplyfly.model.Payment;
import com.simplyfly.model.Refund;
import com.simplyfly.repository.BookingRepository;
import com.simplyfly.repository.PaymentRepository;
import com.simplyfly.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RefundService{

    private final RefundRepository refundRepository;
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    public RefundResponse initiateRefund(Long bookingId){
        Booking booking=bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if(booking.getBookingStatus()!=BookingStatus.CANCELLED){
            throw new RuntimeException("Booking must be cancelled before refund");
        }

        if(refundRepository.findByBookingId(bookingId).isPresent()){
            throw new RuntimeException("Refund already initiated for this booking");
        }

        Payment payment=paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        Refund refund=Refund.builder()
                .booking(booking)
                .amount(payment.getAmount())
                .refundStatus(RefundStatus.INITIATED)
                .build();

        return mapToResponse(refundRepository.save(refund));
    }

    public RefundResponse getRefundByBookingId(Long bookingId){
        Refund refund=refundRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Refund not found"));

        return mapToResponse(refund);
    }

    public List<RefundResponse> getAllRefunds(){
        return refundRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public RefundResponse updateRefundStatus(Long id){
        Refund refund=refundRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Refund not found"));

        refund.setRefundStatus(RefundStatus.PROCESSED);

        return mapToResponse(refundRepository.save(refund));
    }

    private RefundResponse mapToResponse(Refund r){
        return new RefundResponse(
                r.getId(),
                r.getBooking().getId(),
                r.getAmount(),
                r.getRefundStatus(),
                r.getRefundDate()
        );
    }
}