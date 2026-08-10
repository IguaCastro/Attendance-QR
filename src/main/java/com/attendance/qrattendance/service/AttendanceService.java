package com.attendance.qrattendance.service;

import com.attendance.qrattendance.model.Attendance;
import com.attendance.qrattendance.model.ClassSession;
import com.attendance.qrattendance.model.Student;
import com.attendance.qrattendance.repository.AttendanceRepository;
import com.attendance.qrattendance.repository.ClassSessionRepository;
import com.attendance.qrattendance.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final ClassSessionRepository classSessionRepository;

    public AttendanceService(
            AttendanceRepository attendanceRepository,
            StudentRepository studentRepository,
            ClassSessionRepository classSessionRepository) {

        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.classSessionRepository = classSessionRepository;
    }

    public Attendance registerAttendance(String studentCode, Long classSessionId) {

        Student student = studentRepository.findByStudentCode(studentCode);

        if (student == null) {
            return null;
        }

        ClassSession classSession = classSessionRepository
                .findById(classSessionId)
                .orElse(null);

        if (classSession == null) {
            return null;
        }

        boolean alreadyRegistered = attendanceRepository.existsByStudentAndClassSession(
                student,
                classSession);

        if (alreadyRegistered) {
            return new Attendance();
        }

        Attendance attendance = new Attendance();

        attendance.setStudent(student);
        attendance.setClassSession(classSession);
        attendance.setAttendanceTime(LocalDateTime.now());

        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }
}
