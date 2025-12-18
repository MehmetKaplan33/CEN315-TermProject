package com.kaplan.gym_service.service;

import com.kaplan.gym_service.model.ClassSession;
import com.kaplan.gym_service.model.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FormalVerificationTest {

    private final PricingService pricingService = new PricingService();

    @Test
    @DisplayName("Invariant Violation: Doluluk kapasiteyi aşarsa hata fırlatmalı")
    void shouldThrowException_WhenInvariantViolated_OccupancyExceedsCapacity() {
        // GIVEN:
        ClassSession invalidSession = new ClassSession();
        invalidSession.setCapacity(10);
        invalidSession.setOccupiedSlots(15);

        Member member = new Member();
        member.setType(Member.MembershipType.STANDARD);

        // WHEN & THEN:
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            pricingService.calculatePrice(member, invalidSession, 100.0);
        });

        assertTrue(exception.getMessage().contains("Invariant Violation"));
    }

    @Test
    @DisplayName("Pre-condition Violation: Taban fiyat negatif olamaz")
    void shouldThrowException_WhenPreConditionViolated_NegativeBasePrice() {
        // GIVEN:
        ClassSession session = new ClassSession();
        session.setCapacity(20);
        session.setOccupiedSlots(5);

        Member member = new Member();
        member.setType(Member.MembershipType.PREMIUM);

        // WHEN & THEN:
        assertThrows(IllegalArgumentException.class, () -> {
            pricingService.calculatePrice(member, session, -50.0);
        });
    }

    @Test
    @DisplayName("Pre-condition Violation: Parametreler null olamaz")
    void shouldThrowException_WhenPreConditionViolated_NullInput() {
        // GIVEN:
        ClassSession session = new ClassSession();

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> {
            pricingService.calculatePrice(null, session, 100.0);
        });
    }
}