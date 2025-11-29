package com.kaplan.gym_service.service;


import com.kaplan.gym_service.model.ClassSession;
import com.kaplan.gym_service.repository.ClassSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassSessionService {

    private final ClassSessionRepository classSessionRepository;

    public ClassSessionService(ClassSessionRepository classSessionRepository) {
        this.classSessionRepository = classSessionRepository;
    }

    public ClassSession createClassSession(ClassSession session) {
        return classSessionRepository.save(session);
    }

    public List<ClassSession> getAllClassSessions() {
        return classSessionRepository.findAll();
    }
}
