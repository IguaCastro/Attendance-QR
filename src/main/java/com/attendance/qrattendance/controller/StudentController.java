package com.attendance.qrattendance.controller;

import com.attendance.qrattendance.model.Student;
import com.attendance.qrattendance.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String saveStudent(
            @ModelAttribute Student student,
            RedirectAttributes redirectAttributes) {

        boolean editing = student.getId() != null;

        studentService.saveStudent(student);

        if (editing) {
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Student updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Student registered successfully!");
        }

        return "redirect:/students";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        studentService.deleteStudent(id);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Student deleted successfully!");

        return "redirect:/students";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {

        Student student = studentService.getStudentById(id);

        model.addAttribute("student", student);
        model.addAttribute("students", studentService.getAllStudents());

        return "students";
    }

}
