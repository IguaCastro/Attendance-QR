package com.attendance.qrattendance.repository;

import com.attendance.qrattendance.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
