package com.kaplan.gym_service.service;

import com.kaplan.gym_service.model.ClassSession;
import com.kaplan.gym_service.model.Member;
import org.springframework.stereotype.Service;

@Service
public class PricingService {

    public double calculatePrice(Member standardMember, ClassSession session, double basePrice) {
        // İleride buraya indirim ve zam kuralları gelecek.
        // Şimdilik sadece taban fiyatı döndürüyoruz, çünkü Standart üye indirimsiz öder.
        return basePrice;
    }
}