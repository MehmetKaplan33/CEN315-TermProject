package com.kaplan.gym_service.service;

import com.kaplan.gym_service.model.ClassSession;
import com.kaplan.gym_service.model.Member;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingServiceDecisionTableTest {

    private final PricingService pricingService = new PricingService();

    // Decision Table
    // Format: "Üyelik Tipi, Kapasite, Dolu Koltuk, Taban Fiyat, Beklenen Sonuç"
    @ParameterizedTest(name = "Senaryo {index}: {0} üye, {2}/{1} doluluk -> Fiyat {4} olmalı")
    @CsvSource({

            "STANDARD, 10, 5, 100.0, 100.0",
            "STANDARD, 10, 9, 100.0, 150.0",

            "PREMIUM,  10, 5, 100.0, 90.0",
            "PREMIUM,  10, 9, 100.0, 135.0",

            "STUDENT,  10, 5, 100.0, 50.0",
            "STUDENT,  10, 9, 100.0, 75.0"
    })
    void shouldCalculatePriceBasedOnDecisionTable(
            Member.MembershipType type,
            int capacity,
            int occupied,
            double basePrice,
            double expectedPrice
    ) {
        // GIVEN
        Member member = new Member();
        member.setType(type);

        ClassSession session = new ClassSession();
        session.setCapacity(capacity);
        session.setOccupiedSlots(occupied);

        // WHEN
        double actualPrice = pricingService.calculatePrice(member, session, basePrice);

        // THEN
        assertEquals(expectedPrice, actualPrice, 0.01);
    }
}