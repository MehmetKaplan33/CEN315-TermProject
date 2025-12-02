package com.kaplan.gym_service.controller;

import com.kaplan.gym_service.model.Reservation;
import com.kaplan.gym_service.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    void shouldCreateReservation() throws Exception {
        given(reservationService.createReservation(any())).willReturn(new Reservation());

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"memberId\": 1, \"classId\": 1}"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCancelReservation() throws Exception {
        doNothing().when(reservationService).cancelReservation(1L);

        mockMvc.perform(delete("/reservations/1"))
                .andExpect(status().isOk());
    }
}