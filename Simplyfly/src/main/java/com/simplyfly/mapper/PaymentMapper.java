package com.simplyfly.mapper;

import com.simplyfly.dto.request.PaymentRequest;
import com.simplyfly.dto.response.PaymentResponse;
import com.simplyfly.enums.PaymentStatus;
import com.simplyfly.model.Booking;
import com.simplyfly.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper{

    public Payment mapToEntity(PaymentRequest dto,Booking booking){
        Payment payment=new Payment();
        payment.setBooking(booking);
        payment.setAmount(dto.amount());
        payment.setPaymentMethod(dto.paymentMethod());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        return payment;
    }

    public PaymentResponse mapToResponse(Payment p){
        return new PaymentResponse(
                p.getId(),
                p.getBooking().getId(),
                p.getAmount(),
                p.getPaymentStatus(),
                p.getPaymentMethod(),
                p.getPaymentDate()
        );
    }
}