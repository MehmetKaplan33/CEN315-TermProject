package com.kaplan.gym_service.controller;

import com.kaplan.gym_service.model.ClassSession;
import com.kaplan.gym_service.service.ClassSessionService; // Service importu
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/classes")
public class ClassController {

    private final ClassSessionService classSessionService;

    public ClassController(ClassSessionService classSessionService) {
        this.classSessionService = classSessionService;
    }

    @PostMapping
    public ClassSession createClass(@RequestBody ClassSession session) {
        return classSessionService.createClassSession(session);
    }

    @GetMapping
    public List<ClassSession> getAllClasses() {
        return classSessionService.getAllClassSessions();
    }

    @DeleteMapping("/{id}")
    public void deleteClass(@PathVariable Long id) {
        classSessionService.deleteClassSession(id);
    }
}