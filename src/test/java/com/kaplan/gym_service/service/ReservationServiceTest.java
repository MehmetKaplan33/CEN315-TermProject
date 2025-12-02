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
        // GIVEN
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

        // WHEN
        Reservation result = reservationService.createReservation(request);

        // THEN
        assertNotNull(result);
        assertEquals(100.0, result.getPrice());

        assertEquals(member, result.getMember());
        assertEquals(session, result.getClassSession());

        assertEquals(Reservation.ReservationStatus.CONFIRMED, result.getStatus());
        assertNotNull(result.getReservationTime());
        assertEquals(6, session.getOccupiedSlots());

        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(classSessionRepository, times(1)).save(session);
    }

    // SENARYO 2: Hata Durumu (Sınıf Dolu)
    @Test
    void shouldThrowException_WhenClassIsFull() {
        // GIVEN
        CreateReservationRequest request = new CreateReservationRequest();
        request.setMemberId(1L);
        request.setClassId(1L);

        ClassSession session = new ClassSession();
        session.setCapacity(10);
        session.setOccupiedSlots(10);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(new Member()));
        when(classSessionRepository.findById(1L)).thenReturn(Optional.of(session));

        // WHEN & THEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservationService.createReservation(request);
        });

        assertEquals("Class is full! Reservation rejected.", exception.getMessage());
        verify(reservationRepository, never()).save(any());
    }

    // SENARYO 3: İptal İşlemi (Normal Durum)
    @Test
    void shouldCancelReservation_AndDecreaseOccupiedSlots() {
        // GIVEN
        long reservationId = 1L;

        ClassSession session = new ClassSession();
        session.setId(1L);
        session.setCapacity(10);
        session.setOccupiedSlots(5);

        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setClassSession(session);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        // WHEN
        reservationService.cancelReservation(reservationId);

        // THEN
        assertEquals(4, session.getOccupiedSlots());

        verify(classSessionRepository).save(session);
        verify(reservationRepository).deleteById(reservationId);
    }

    // SENARYO 4: Sınıf zaten boşsa eksiye düşmemeli
    @Test
    void shouldNotDecreaseOccupiedSlots_WhenAlreadyZero() {
        // GIVEN
        long reservationId = 1L;
        ClassSession session = new ClassSession();
        session.setOccupiedSlots(0);

        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setClassSession(session);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        // WHEN
        reservationService.cancelReservation(reservationId);

        // THEN
        assertEquals(0, session.getOccupiedSlots());
        verify(classSessionRepository, never()).save(any());
        verify(reservationRepository).deleteById(reservationId);
    }
}