package com.senguichet.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column(nullable = false)
    private String name;

    // @Column(nullable = false)
    private String description;

    // @Column(nullable = false)
    private LocalDateTime date;

    // @Column(nullable = false)
    private String location;

    // @Column(nullable = false)
    private double price;

    // @Column(nullable = false)
    private int capacity;
}