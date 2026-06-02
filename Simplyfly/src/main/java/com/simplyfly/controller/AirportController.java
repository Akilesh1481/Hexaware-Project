package com.simplyfly.controller;

import com.simplyfly.dto.request.AirportRequest;
import com.simplyfly.dto.response.AirportResponse;
import com.simplyfly.dto.response.PageResponse;
import com.simplyfly.service.AirportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    public ResponseEntity<AirportResponse> addAirport(
            @Valid @RequestBody AirportRequest request) {
        return ResponseEntity.ok(airportService.addAirport(request));
    }

    @GetMapping
    public ResponseEntity<PageResponse<AirportResponse>> getAllAirports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
                airportService.getAllAirports(page, size));
    }

    @GetMapping("{id}")
    public ResponseEntity<AirportResponse> getAirportById(
            @PathVariable Long id) {
        return ResponseEntity.ok(airportService.getAirportById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<AirportResponse> getByCode(
            @PathVariable String code) {
        return ResponseEntity.ok(airportService.getAirportByCode(code));
    }

    @GetMapping("/search")
    public ResponseEntity<List<AirportResponse>> searchByCity(
            @RequestParam String city) {
        return ResponseEntity.ok(airportService.searchByCity(city));
    }

    @PutMapping("{id}")
    public ResponseEntity<AirportResponse> updateAirport(
            @PathVariable Long id,
            @Valid @RequestBody AirportRequest request) {
        return ResponseEntity.ok(
                airportService.updateAirport(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAirport(@PathVariable Long id) {
        airportService.deleteAirport(id);
        return ResponseEntity.ok("Airport deleted successfully");
    }
}