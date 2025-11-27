package com.kaplan.gym_service.service;

import com.kaplan.gym_service.model.ClassSession;
import com.kaplan.gym_service.model.Member;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {

    // Testimiz için servisi oluşturuyoruz
    PricingService pricingService = new PricingService();

    @Test
    void shouldReturnBasePrice_WhenMemberIsStandard() {
        // 1. GIVEN (Verilenler)
        // Standart bir üye oluşturalım
        Member standardMember = new Member();
        standardMember.setType(Member.MembershipType.STANDARD);

        // Bir ders oluşturalım (Tarihi, kapasitesi şimdilik önemli değil)
        ClassSession session = new ClassSession();
        session.setDateTime(LocalDateTime.now().plusDays(1)); // Yarına ders
        session.setCapacity(20);

        double basePrice = 100.0; // Dersin taban fiyatı 100 TL olsun

        // 2. WHEN (Olay Anı)
        // HATA ALACAKSIN: Çünkü henüz calculatePrice diye bir metodumuz yok!
        double price = pricingService.calculatePrice(standardMember, session, basePrice);

        // 3. THEN (Beklenen Sonuç)
        // Standart üye olduğu için fiyat 100.0 kalmalı
        assertEquals(100.0, price);
    }

    @Test
    void shouldIncreasePrice_WhenOccupancyIsHigh() {
        // 1. GIVEN
        Member member = new Member();
        member.setType(Member.MembershipType.STANDARD);

        ClassSession session = new ClassSession();
        session.setCapacity(10);      // Kapasite 10 kişi
        session.setOccupiedSlots(9);  // 9 kişi dolu (%90 doluluk > %80)

        double basePrice = 100.0;

        // 2. WHEN
        double price = pricingService.calculatePrice(member, session, basePrice);

        // 3. THEN
        // %50 zam bekliyoruz (100 * 1.5 = 150)
        assertEquals(150.0, price);
    }
}