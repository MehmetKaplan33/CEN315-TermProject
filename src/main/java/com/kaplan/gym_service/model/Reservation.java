package com.kaplan.gym_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "class_session_id")
    private ClassSession classSession;

    private LocalDateTime reservationTime;

    private double price;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public enum ReservationStatus {
        CONFIRMED,
        CANCELLED,
        PENDING
    }

    public void confirm() {
        if (this.status == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Cannot confirm a cancelled reservation.");
        }
        this.status = ReservationStatus.CONFIRMED;
    }

    public void cancel() {
        if (this.status == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Reservation is already cancelled.");
        }
        this.status = ReservationStatus.CANCELLED;
    }
}