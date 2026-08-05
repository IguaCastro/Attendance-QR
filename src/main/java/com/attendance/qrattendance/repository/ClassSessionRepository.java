package com.attendance.qrattendance.repository;

import com.attendance.qrattendance.model.ClassSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassSessionRepository extends JpaRepository<ClassSession, Long> {

}