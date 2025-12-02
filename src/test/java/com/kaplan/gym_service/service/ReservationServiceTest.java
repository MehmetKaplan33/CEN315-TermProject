package com.kaplan.gym_service.service;

import com.kaplan.gym_service.dto.CreateReservationRequest;
import com.kaplan.gym_service.model.ClassSession;
import com.kaplan.gym_service.model.Member;
import com.kaplan.gym_service.model.Reservation;
import com.kaplan.gym_service.repository.ClassSessionRepository;
import com.kaplan.gym_service.repository.MemberRepository;
import com.kaplan.gym_service.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ClassSessionRepository classSessionRepository;
    @Mock
    private PricingService pricingService;

    @InjectMocks
    private ReservationService reservationService;

    // SENARYO 1: Normal Durum (Rezervasyon Başarılı)
    @Test
    void shouldCreateReservation_WhenSeatsAreAvailable() {
        // 1. GIVEN
        CreateReservationRequest request = new CreateReservationRequest();
        request.setMemberId(1L);
        request.setClassId(1L);

        Member member = new Member();
        member.setId(1L);

        ClassSession session = new ClassSession();
        session.setId(1L);
        session.setCapacity(10);
        session.setOccupiedSlots(5);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(classSessionRepository.findById(1L)).thenReturn(Optional.of(session));

        when(pricingService.calculatePrice(any(), any(), anyDouble())).thenReturn(100.0);

        when(reservationRepository.save(any(Reservation.class))).thenAnswer(i -> i.getArguments()[0]);

        // 2. WHEN
        Reservation result = reservationService.createReservation(request);

        // 3. THEN
        assertNotNull(result);
        assertEquals(100.0, result.getPrice());

        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    // SENARYO 2: Hata Durumu (Sınıf Dolu)
    @Test
    void shouldThrowException_WhenClassIsFull() {
        // 1. GIVEN
        CreateReservationRequest request = new CreateReservationRequest();
        request.setMemberId(1L);
        request.setClassId(1L);

        ClassSession session = new ClassSession();
        session.setCapacity(10);
        session.setOccupiedSlots(10);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(new Member()));
        when(classSessionRepository.findById(1L)).thenReturn(Optional.of(session));

        // 2. WHEN & THEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservationService.createReservation(request);
        });

        assertEquals("Class is full! Reservation rejected.", exception.getMessage());

        verify(reservationRepository, never()).save(any());
    }
}