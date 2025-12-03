package com.kaplan.gym_service.service;

import com.kaplan.gym_service.model.ClassSession;
import com.kaplan.gym_service.model.Member;
import org.springframework.stereotype.Service;

@Service
public class PricingService {

    public double calculatePrice(Member member, ClassSession session, double basePrice) {
        double finalPrice = basePrice;

        // 1. Membership Discounts (Üyelik İndirimleri)
        if (member.getType() == Member.MembershipType.PREMIUM) {
            finalPrice = finalPrice * 0.90; // %10 İndirim
        } else if (member.getType() == Member.MembershipType.STUDENT) {
            finalPrice = finalPrice * 0.50; // %50 İndirim
        }
        //Standard üyeler için indirim yok

        // 2. Surge Pricing (Doluluk Zammı)
        double occupancyRate = (double) session.getOccupiedSlots() / session.getCapacity();
        if (occupancyRate > 0.80) {
            finalPrice = finalPrice * 1.50; // %50 Zam
        }

        return finalPrice;
    }
}