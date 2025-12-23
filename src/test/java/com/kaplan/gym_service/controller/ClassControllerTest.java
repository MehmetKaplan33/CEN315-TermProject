package com.kaplan.gym_service.controller;

import com.kaplan.gym_service.model.ClassSession;
import com.kaplan.gym_service.service.ClassSessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClassController.class)
class ClassControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClassSessionService classSessionService;

    @Test
    void shouldReturnAllClasses() throws Exception {
        given(classSessionService.getAllClassSessions()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/classes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateClass() throws Exception {
        // GIVEN
        ClassSession newClass = new ClassSession();
        newClass.setId(1L);
        newClass.setName("Pilates");

        given(classSessionService.createClassSession(any())).willReturn(newClass);

        // WHEN & THEN
        mockMvc.perform(post("/classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Pilates\", \"capacity\": 10}"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteClass() throws Exception {
        // WHEN & THEN
        mockMvc.perform(delete("/classes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}