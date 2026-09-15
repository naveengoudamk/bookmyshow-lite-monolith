package com.bookmyshow.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "booking_seats")
public class BookingSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @OneToOne
    @JoinColumn(name = "show_seat_id", unique = true, nullable = false)
    private ShowSeat showSeat;
}