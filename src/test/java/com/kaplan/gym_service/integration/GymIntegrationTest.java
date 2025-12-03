package com.kaplan.gym_service.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaplan.gym_service.dto.CreateReservationRequest;
import com.kaplan.gym_service.model.ClassSession;
import com.kaplan.gym_service.model.Member;
import com.kaplan.gym_service.repository.ClassSessionRepository;
import com.kaplan.gym_service.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class GymIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ClassSessionRepository classSessionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateReservation_EndToEnd() throws Exception {
        // --- 1. HAZIRLIK ---
        Member member = new Member();
        member.setName("Integration User");
        member.setEmail("integration@test.com");
        member.setType(Member.MembershipType.STANDARD);
        member = memberRepository.save(member);

        ClassSession session = new ClassSession();
        session.setName("Integration Yoga");
        session.setCapacity(10);
        session.setOccupiedSlots(0);
        session.setDateTime(LocalDateTime.now().plusDays(1));
        session = classSessionRepository.save(session);

        // --- 2. İSTEK HAZIRLAMA ---
        CreateReservationRequest request = new CreateReservationRequest();
        request.setMemberId(member.getId());
        request.setClassId(session.getId());

        // --- 3. EYLEM VE KONTROL ---
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONFIRMED"))
                .andExpect(jsonPath("$.price").value(100.0));
    }
}