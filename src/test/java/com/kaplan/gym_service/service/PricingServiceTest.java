package com.kaplan.gym_service.service;

import com.kaplan.gym_service.model.ClassSession;
import com.kaplan.gym_service.model.Member;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {

    PricingService pricingService = new PricingService();

    @Test
    void shouldReturnBasePrice_WhenMemberIsStandard() {
        // 1. GIVEN
        Member standardMember = new Member();
        standardMember.setType(Member.MembershipType.STANDARD);

        ClassSession session = new ClassSession();
        session.setDateTime(LocalDateTime.now().plusDays(1)); // Yarına ders
        session.setCapacity(20);

        double basePrice = 100.0; // Dersin taban fiyatı 100 TL olsun

        // 2. WHEN
        double price = pricingService.calculatePrice(standardMember, session, basePrice);

        // 3. THEN
        assertEquals(100.0, price);
    }

    @Test
    void shouldIncreasePrice_WhenOccupancyIsHigh() {
        // 1. GIVEN
        Member member = new Member();
        member.setType(Member.MembershipType.STANDARD);

        ClassSession session = new ClassSession();
        session.setCapacity(10);
        session.setOccupiedSlots(9);

        double basePrice = 100.0;

        // 2. WHEN
        double price = pricingService.calculatePrice(member, session, basePrice);

        // 3. THEN
        assertEquals(150.0, price);
    }
}