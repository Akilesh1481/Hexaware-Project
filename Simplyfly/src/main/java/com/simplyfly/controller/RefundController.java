package com.simplyfly.controller;

import com.simplyfly.dto.response.RefundResponse;
import com.simplyfly.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/refunds")
@RequiredArgsConstructor
public class RefundController{

    private final RefundService refundService;

    @PostMapping("/{bookingId}")
    public ResponseEntity<RefundResponse> initiateRefund(@PathVariable Long bookingId){
        return ResponseEntity.ok(refundService.initiateRefund(bookingId));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<RefundResponse> getRefundByBookingId(@PathVariable Long bookingId){
        return ResponseEntity.ok(refundService.getRefundByBookingId(bookingId));
    }

    @GetMapping
    public ResponseEntity<List<RefundResponse>> getAllRefunds(){
        return ResponseEntity.ok(refundService.getAllRefunds());
    }

    @PutMapping("/{id}/process")
    public ResponseEntity<RefundResponse> processRefund(@PathVariable Long id){
        return ResponseEntity.ok(refundService.updateRefundStatus(id));
    }
}