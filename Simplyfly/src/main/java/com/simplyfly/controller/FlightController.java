package com.simplyfly.controller;
import com.simplyfly.dto.request.FlightRequest;
import com.simplyfly.dto.response.FlightResponse;
import com.simplyfly.dto.response.PageResponse;
import com.simplyfly.enums.FlightStatus;
import com.simplyfly.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightResponse> addFlight(
            @Valid @RequestBody FlightRequest request) {
        return ResponseEntity.ok(flightService.addFlight(request));
    }

    @GetMapping
    public ResponseEntity<PageResponse<FlightResponse>> getAllFlights(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
                flightService.getAllFlights(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> getFlightById(
            @PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<FlightResponse>> searchFlights(
            @RequestParam Long sourceId,
            @RequestParam Long destId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime date) {
        return ResponseEntity.ok(
                flightService.searchFlights(sourceId, destId, date));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<FlightResponse>> getByOwner(
            @PathVariable Long ownerId) {
        return ResponseEntity.ok(
                flightService.getFlightsByOwner(ownerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> updateFlight(
            @PathVariable Long id,
            @Valid @RequestBody FlightRequest request) {
        return ResponseEntity.ok(
                flightService.updateFlight(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<FlightResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam FlightStatus status) {
        return ResponseEntity.ok(
                flightService.updateFlightStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok("Flight deleted successfully");
    }
}