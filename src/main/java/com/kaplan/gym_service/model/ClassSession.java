package com.kaplan.gym_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "classes")
@Data
public class ClassSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String instructor;

    private int capacity;
    private int occupiedSlots = 0;

    private LocalDateTime dateTime;
}