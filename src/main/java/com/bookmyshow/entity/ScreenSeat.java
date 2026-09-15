package com.bookmyshow.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "screen_seats")
public class ScreenSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "seat_type")
    private String seatType;

    @ManyToOne
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;
}