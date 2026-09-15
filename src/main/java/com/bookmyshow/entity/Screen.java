package com.bookmyshow.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "screens")
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "screen_name")
    private String screenName;

    @Column(name = "total_seats")
    private Integer totalSeats;

    @ManyToOne
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;
}