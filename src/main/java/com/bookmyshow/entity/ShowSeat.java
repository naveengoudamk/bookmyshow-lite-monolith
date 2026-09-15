package com.bookmyshow.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "show_seats")
public class ShowSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Column(name = "seat_type", nullable = false)
    private String seatType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status;

    @Column(nullable = false)
    private Double price;

    @Version
    private Long version;

    // Temporary hold information
    @Column(name = "held_by")
    private String heldBy;

    @Column(name = "hold_expiry_time")
    private LocalDateTime holdExpiryTime;
}