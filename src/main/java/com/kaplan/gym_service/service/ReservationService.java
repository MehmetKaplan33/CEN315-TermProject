package com.kaplan.gym_service.service;

import com.kaplan.gym_service.dto.CreateReservationRequest;
import com.kaplan.gym_service.model.ClassSession;
import com.kaplan.gym_service.model.Member;
import com.kaplan.gym_service.model.Reservation;
import com.kaplan.gym_service.repository.ClassSessionRepository;
import com.kaplan.gym_service.repository.MemberRepository;
import com.kaplan.gym_service.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final ClassSessionRepository classSessionRepository;
    private final PricingService pricingService;

    public ReservationService(ReservationRepository reservationRepository,
                              MemberRepository memberRepository,
                              ClassSessionRepository classSessionRepository,
                              PricingService pricingService) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.classSessionRepository = classSessionRepository;
        this.pricingService = pricingService;
    }

    @Transactional
    public Reservation createReservation(CreateReservationRequest request) {
        // 1. Üyeyi ve Dersi Bul
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        ClassSession session = classSessionRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        boolean alreadyExists = reservationRepository.existsByMemberIdAndClassSessionIdAndStatus(
                request.getMemberId(),
                request.getClassId(),
                Reservation.ReservationStatus.CONFIRMED
        );

        if (alreadyExists) {
            throw new RuntimeException("User is already registered for this class!");
        }

        // 2. KAPASİTE KONTROLÜ
        if (session.getOccupiedSlots() >= session.getCapacity()) {
            throw new RuntimeException("Class is full! Reservation rejected.");
        }

        // 3. FİYAT HESAPLAMA
        double basePrice = 100.0;
        double finalPrice = pricingService.calculatePrice(member, session, basePrice);

        // 4. Rezervasyonu Oluştur
        Reservation reservation = new Reservation();
        reservation.setMember(member);
        reservation.setClassSession(session);
        reservation.setPrice(finalPrice);
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setStatus(Reservation.ReservationStatus.CONFIRMED);

        // 5. Sınıfın Doluluğunu Artır (+1)
        session.setOccupiedSlots(session.getOccupiedSlots() + 1);
        classSessionRepository.save(session);

        // 6. Kaydet ve Döndür
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        ClassSession session = reservation.getClassSession();

        if (session.getOccupiedSlots() > 0) {
            session.setOccupiedSlots(session.getOccupiedSlots() - 1);
            classSessionRepository.save(session);
        }

        reservationRepository.deleteById(id);
    }
}