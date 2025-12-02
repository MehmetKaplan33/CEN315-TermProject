package com.kaplan.gym_service.service;

import com.kaplan.gym_service.model.ClassSession;
import com.kaplan.gym_service.repository.ClassSessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClassSessionServiceTest {

    @Mock
    private ClassSessionRepository classSessionRepository;

    @InjectMocks
    private ClassSessionService classSessionService;

    @Test
    void shouldCreateClass() {
        // GIVEN
        ClassSession session = new ClassSession();
        session.setName("Yoga");

        when(classSessionRepository.save(any(ClassSession.class))).thenReturn(session);

        // WHEN
        ClassSession result = classSessionService.createClassSession(session);

        // THEN
        assertNotNull(result);
        assertEquals("Yoga", result.getName());

        verify(classSessionRepository).save(session);
    }

    @Test
    void shouldGetAllClasses() {
        // WHEN
        classSessionService.getAllClassSessions();

        // THEN
        verify(classSessionRepository).findAll();
    }
}