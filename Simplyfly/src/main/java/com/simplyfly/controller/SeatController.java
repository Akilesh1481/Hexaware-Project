package com.simplyfly.controller;

import com.simplyfly.dto.request.SeatRequest;
import com.simplyfly.dto.response.SeatResponse;
import com.simplyfly.service.SeatService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/seats")
@AllArgsConstructor
public class SeatController {

    private final SeatService seatService;

    // ADD single seat
    @PostMapping
    public ResponseEntity<SeatResponse> addSeat(
            @Valid @RequestBody SeatRequest request) {
        return ResponseEntity.ok(seatService.addSeat(request));
    }

    // ADD multiple seats auto-generated for a flight
    @PostMapping("/flight/{flightId}/generate/{totalSeats}")
    public ResponseEntity<List<SeatResponse>> generateSeats(
            @PathVariable Long flightId,
            @PathVariable int totalSeats) {
        return ResponseEntity.ok(
                seatService.addSeatsForFlight(flightId, totalSeats));
    }

    // GET all seats
    @GetMapping
    public ResponseEntity<List<SeatResponse>> getAllSeats() {
        return ResponseEntity.ok(seatService.getAllSeats());
    }

    // GET seat by id
    @GetMapping("/{id}")
    public ResponseEntity<SeatResponse> getSeatById(
            @PathVariable Long id) {
        return ResponseEntity.ok(seatService.getSeatById(id));
    }

    // GET all seats by flight
    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<SeatResponse>> getSeatsByFlight(
            @PathVariable Long flightId) {
        return ResponseEntity.ok(
                seatService.getSeatsByFlight(flightId));
    }

    // GET available seats by flight
    @GetMapping("/flight/{flightId}/available")
    public ResponseEntity<List<SeatResponse>> getAvailableSeats(
            @PathVariable Long flightId) {
        return ResponseEntity.ok(
                seatService.getAvailableSeatsByFlight(flightId));
    }

    // GET booked seats by flight
    @GetMapping("/flight/{flightId}/booked")
    public ResponseEntity<List<SeatResponse>> getBookedSeats(
            @PathVariable Long flightId) {
        return ResponseEntity.ok(
                seatService.getBookedSeatsByFlight(flightId));
    }

    // UPDATE seat status
    @PatchMapping("/{id}/status")
    public ResponseEntity<SeatResponse> updateSeatStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(
                seatService.updateSeatStatus(id, status));
    }

    // DELETE seat
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return ResponseEntity.ok("Seat deleted successfully");
    }
}