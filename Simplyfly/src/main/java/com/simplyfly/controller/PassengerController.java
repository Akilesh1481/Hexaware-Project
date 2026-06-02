package com.simplyfly.controller;

import com.simplyfly.dto.request.PassengerRequest;
import com.simplyfly.dto.response.PassengerResponse;
import com.simplyfly.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping
    public ResponseEntity<PassengerResponse> addPassenger(
            @Valid @RequestBody PassengerRequest request) {
        return ResponseEntity.ok(
                passengerService.addPassenger(request));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<PassengerResponse>> getByBooking(
            @PathVariable Long bookingId) {
        return ResponseEntity.ok(
                passengerService.getPassengersByBooking(bookingId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePassenger(
            @PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.ok("Passenger deleted successfully");
    }
}