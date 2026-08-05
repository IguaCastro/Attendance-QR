package com.attendance.qrattendance.controller;

import com.attendance.qrattendance.model.Student;
import com.attendance.qrattendance.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public String students(Model model) {

        model.addAttribute("student", new Student());
        model.addAttribute("students", studentService.getAllStudents());

        return "students";
    }

    @PostMapping("/students/save")
    public String saveStudent(@ModelAttribute Student student) {

        studentService.saveStudent(student);

        return "redirect:/students";
    }

}
