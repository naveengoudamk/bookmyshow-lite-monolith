package com.bookmyshow.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "theatres")
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String city;

    private String address;
}