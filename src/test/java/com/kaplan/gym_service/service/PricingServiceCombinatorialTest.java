package com.kaplan.gym_service.service;

import com.kaplan.gym_service.model.ClassSession;
import com.kaplan.gym_service.model.Member;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PricingServiceCombinatorialTest {

    private final PricingService pricingService = new PricingService();

    @ParameterizedTest
    @CsvFileSource(resources = "/pricing_test_set.csv", numLinesToSkip = 1)
    void shouldCalculatePrice_UsingActsGeneratedData(String memberTypeStr, String occupancyStr, String basePriceStr) {
        // 1. GIVEN

        Member member = new Member();
        member.setType(Member.MembershipType.valueOf(memberTypeStr));

        ClassSession session = new ClassSession();
        session.setCapacity(10);

        int occupiedSlots = 0;
        if ("HIGH".equals(occupancyStr)) {
            occupiedSlots = 9; // %90
        } else {
            occupiedSlots = 2; // %20
        }
        session.setOccupiedSlots(occupiedSlots);

        double basePrice = 0.0;
        switch (basePriceStr) {
            case "LOW" -> basePrice = 50.0;
            case "MEDIUM" -> basePrice = 100.0;
            case "HIGH" -> basePrice = 200.0;
        }

        // 2. WHEN
        double calculatedPrice = pricingService.calculatePrice(member, session, basePrice);

        // 3. THEN
        System.out.printf("Test Case: %-10s | Occupancy: %-4s | Base: %-6s -> Result: %.2f%n",
                memberTypeStr, occupancyStr, basePriceStr, calculatedPrice);

        assertTrue(calculatedPrice >= 0, "Fiyat negatif olmamalı");
    }
}