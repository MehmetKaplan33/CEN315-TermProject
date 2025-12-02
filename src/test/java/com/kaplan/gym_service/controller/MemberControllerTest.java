package com.kaplan.gym_service.controller;

import com.kaplan.gym_service.model.Member;
import com.kaplan.gym_service.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    void shouldReturnAllMembers() throws Exception {
        // GIVEN
        given(memberService.getAllMembers()).willReturn(Collections.emptyList());

        // WHEN & THEN
        mockMvc.perform(get("/members")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnMemberById() throws Exception {
        // GIVEN
        Member member = new Member();
        member.setId(1L);
        member.setName("Ahmet");

        given(memberService.getMemberById(1L)).willReturn(member);

        // WHEN & THEN
        mockMvc.perform(get("/members/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateMember() throws Exception {
        //GIVEN
        Member newMember = new Member();
        newMember.setId(1L);
        newMember.setName("Test User");

        given(memberService.createMember(any(Member.class))).willReturn(newMember);

        // WHEN & THEN
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test User\", \"type\": \"STANDARD\"}"))
                .andExpect(status().isOk());
    }
}