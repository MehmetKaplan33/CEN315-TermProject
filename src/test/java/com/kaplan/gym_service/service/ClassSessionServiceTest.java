package com.kaplan.gym_service.service;

import com.kaplan.gym_service.model.ClassSession;
import com.kaplan.gym_service.repository.ClassSessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        ClassSession session = new ClassSession();
        when(classSessionRepository.save(any(ClassSession.class))).thenReturn(session);

        classSessionService.createClassSession(session);

        verify(classSessionRepository).save(session);
    }

    @Test
    void shouldGetAllClasses() {
        classSessionService.getAllClassSessions();
        verify(classSessionRepository).findAll();
    }
}