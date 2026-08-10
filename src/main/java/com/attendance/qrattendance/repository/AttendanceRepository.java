package com.attendance.qrattendance.repository;

import com.attendance.qrattendance.model.Attendance;
import com.attendance.qrattendance.model.ClassSession;
import com.attendance.qrattendance.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    boolean existsByStudentAndClassSession(
            Student student,
            ClassSession classSession
    );
}