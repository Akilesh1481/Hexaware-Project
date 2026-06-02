package com.simplyfly.model;
import com.simplyfly.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flight_name", length = 100)
    private String flightName;

    @Column(name = "flight_number", length = 50, unique = true)
    private String flightNumber;

    @ManyToOne(fetch = FetchType.LAZY)

    private Airport sourceAirport;

    @ManyToOne(fetch = FetchType.LAZY)

    private Airport destinationAirport;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "total_seats")
    private Integer totalSeats;

    @Column(name = "available_seats")
    private Integer availableSeats;

    @Column(name = "ticket_price")
    private Double ticketPrice;

    @Column(name = "checkin_baggage_limit")
    private Integer checkinBaggageLimit;

    @Column(name = "cabin_baggage_limit")
    private Integer cabinBaggageLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "flight_status")
    private FlightStatus flightStatus = FlightStatus.SCHEDULED;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
