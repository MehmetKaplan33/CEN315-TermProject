package com.kaplan.gym_service.service;

import com.kaplan.gym_service.model.ClassSession;
import com.kaplan.gym_service.model.Member;
import net.jqwik.api.*;
import net.jqwik.api.constraints.DoubleRange;

import static org.assertj.core.api.Assertions.assertThat;

class PricingServicePropertyTest {

    private final PricingService pricingService = new PricingService();

    // ÖZELLİK 1: Fiyat Hiçbir Zaman Negatif Olamaz
    @Property
    void priceShouldNeverBeNegative(
            @ForAll("members") Member member,
            @ForAll("sessions") ClassSession session,
            @ForAll @DoubleRange(min = 0.0, max = 10000.0) double basePrice
    ) {
        double calculatedPrice = pricingService.calculatePrice(member, session, basePrice);
        assertThat(calculatedPrice).isGreaterThanOrEqualTo(0.0);
    }

    // ÖZELLİK 2: İndirim Mantığı (Student <= Premium <= Standard)
    @Property
    void checkDiscountHierarchy(
            @ForAll("sessions") ClassSession session,
            @ForAll @DoubleRange(min = 0.0, max = 10000.0) double basePrice
    ) {
        // GIVEN
        Member standard = new Member(); standard.setType(Member.MembershipType.STANDARD);
        Member premium = new Member(); premium.setType(Member.MembershipType.PREMIUM);
        Member student = new Member(); student.setType(Member.MembershipType.STUDENT);

        // WHEN
        double pStandard = pricingService.calculatePrice(standard, session, basePrice);
        double pPremium = pricingService.calculatePrice(premium, session, basePrice);
        double pStudent = pricingService.calculatePrice(student, session, basePrice);

        // THEN
        assertThat(pStudent).isLessThanOrEqualTo(pPremium);
        assertThat(pPremium).isLessThanOrEqualTo(pStandard);
    }

    // ÖZELLİK 3: Surge Pricing (Doluluk %80 üstüyse Fiyat Artmalı)
    @Property
    void priceShouldIncrease_WhenOccupancyIsHigh(
            @ForAll("highOccupancySessions") ClassSession session,
            @ForAll @DoubleRange(min = 10.0, max = 1000.0) double basePrice
    ) {
        // GIVEN
        Member member = new Member();
        member.setType(Member.MembershipType.STANDARD);

        // WHEN
        double price = pricingService.calculatePrice(member, session, basePrice);

        // THEN
        assertThat(price).isGreaterThan(basePrice);
    }

    @Provide("members")
    Arbitrary<Member> provideMembers() {
        return Arbitraries.of(Member.MembershipType.values()).map(type -> {
            Member m = new Member(); m.setType(type); return m;
        });
    }

    @Provide("sessions")
    Arbitrary<ClassSession> provideSessions() {
        return Arbitraries.integers().between(1, 100).flatMap(capacity ->
                Arbitraries.integers().between(0, capacity).map(occupied -> {
                    ClassSession s = new ClassSession(); s.setCapacity(capacity); s.setOccupiedSlots(occupied); return s;
                })
        );
    }

    @Provide("highOccupancySessions")
    Arbitrary<ClassSession> provideHighOccupancySessions() {
        return Arbitraries.integers().between(10, 100).flatMap(capacity -> {
            int minOccupied = (int) Math.floor(capacity * 0.80) + 1;

            if (minOccupied > capacity) minOccupied = capacity;

            return Arbitraries.integers().between(minOccupied, capacity).map(occupied -> {
                ClassSession s = new ClassSession();
                s.setCapacity(capacity);
                s.setOccupiedSlots(occupied);
                return s;
            });
        });
    }
}