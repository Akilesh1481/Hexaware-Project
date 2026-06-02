package com.simplyfly.service;

import com.simplyfly.dto.request.PaymentRequest;
import com.simplyfly.dto.response.PaymentResponse;
import com.simplyfly.exception.ResourceNotFoundException;
import com.simplyfly.mapper.PaymentMapper;
import com.simplyfly.model.Booking;
import com.simplyfly.model.Payment;
import com.simplyfly.repository.BookingRepository;
import com.simplyfly.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService{

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentMapper paymentMapper;

    public PaymentResponse makePayment(PaymentRequest request){
        Booking booking=bookingRepository.findById(request.bookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if(paymentRepository.findByBookingId(request.bookingId()).isPresent()){
            throw new RuntimeException("Payment already done for this booking");
        }

        Payment payment=paymentMapper.mapToEntity(request,booking);

        return paymentMapper.mapToResponse(paymentRepository.save(payment));
    }

    public PaymentResponse getPaymentByBookingId(Long bookingId){
        Payment payment=paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        return paymentMapper.mapToResponse(payment);
    }

    public List<PaymentResponse> getAllPayments(){
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::mapToResponse)
                .toList();
    }
}