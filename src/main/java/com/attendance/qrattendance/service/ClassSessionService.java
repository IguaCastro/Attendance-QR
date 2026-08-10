package com.attendance.qrattendance.service;

import com.attendance.qrattendance.model.ClassSession;
import com.attendance.qrattendance.repository.ClassSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassSessionService {

    private final ClassSessionRepository classSessionRepository;

    public ClassSessionService(ClassSessionRepository classSessionRepository) {
        this.classSessionRepository = classSessionRepository;
    }

    public List<ClassSession> getAllClassSessions() {
        return classSessionRepository.findAll();
    }

    public ClassSession saveClassSession(ClassSession classSession) {
        return classSessionRepository.save(classSession);
    }

    public ClassSession getClassSessionById(Long id) {
        return classSessionRepository.findById(id).orElse(null);
    }

    public void deleteClassSession(Long id) {
        classSessionRepository.deleteById(id);
    }
}