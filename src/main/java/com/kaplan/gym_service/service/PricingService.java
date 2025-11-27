package com.kaplan.gym_service.service;

import com.kaplan.gym_service.model.ClassSession;
import com.kaplan.gym_service.model.Member;
import org.springframework.stereotype.Service;

@Service
public class PricingService {

    public double calculatePrice(Member member, ClassSession session, double basePrice) {
        double finalPrice = basePrice;

        // 1. Surge Pricing Kontrolü (Doluluk > %80 ise)
        double occupancyRate = (double) session.getOccupiedSlots() / session.getCapacity();

        if (occupancyRate > 0.80) {
            finalPrice = finalPrice * 1.50; // %50 zam
        }

        return finalPrice;
    }
}