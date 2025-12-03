package com.kaplan.gym_service.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReservationModelTest {

    @Test
    void shouldAllowValidTransitions() {
        // Senaryo: Beklemede olan bir rezervasyonu onaylamak ve onaylanan bir rezervasyonu iptal etmek
        Reservation r1 = new Reservation();
        r1.setStatus(Reservation.ReservationStatus.PENDING);

        Reservation r = new Reservation();
        r.setStatus(Reservation.ReservationStatus.CONFIRMED);

        r.cancel();
        assertEquals(Reservation.ReservationStatus.CANCELLED, r.getStatus());
    }

    @Test
    void shouldPreventInvalidTransitions() {
        // Senaryo: İptal edilmiş bir rezervasyonu tekrar iptal etmeye veya onaylamaya çalışmak
        Reservation r = new Reservation();
        r.setStatus(Reservation.ReservationStatus.CANCELLED);

        assertThrows(IllegalStateException.class, r::cancel);
        assertThrows(IllegalStateException.class, r::confirm);
    }
}