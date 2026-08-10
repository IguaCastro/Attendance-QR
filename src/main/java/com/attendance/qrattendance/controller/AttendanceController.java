package com.attendance.qrattendance.controller;

import com.attendance.qrattendance.model.ClassSession;
import com.attendance.qrattendance.service.AttendanceService;
import com.attendance.qrattendance.service.ClassSessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.attendance.qrattendance.model.Attendance;

@Controller
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final ClassSessionService classSessionService;

    public AttendanceController(
            AttendanceService attendanceService,
            ClassSessionService classSessionService) {

        this.attendanceService = attendanceService;
        this.classSessionService = classSessionService;
    }

    // Página que abre el QR
    @GetMapping("/attendance/register/{classSessionId}")
    public String attendanceForm(
            @PathVariable Long classSessionId,
            Model model) {

        ClassSession classSession = classSessionService.getClassSessionById(classSessionId);

        if (classSession == null) {
            return "redirect:/";
        }

        model.addAttribute("classSession", classSession);

        return "attendance-register";
    }

    // Registrar asistencia
    @PostMapping("/attendance/register")
    public String registerAttendance(
            @RequestParam String studentCode,
            @RequestParam Long classSessionId,
            Model model) {

        Attendance attendance = attendanceService.registerAttendance(
                studentCode,
                classSessionId);

        ClassSession classSession = classSessionService.getClassSessionById(classSessionId);

        model.addAttribute("classSession", classSession);

        if (attendance == null) {

            model.addAttribute(
                    "error",
                    "Student code not found.");

        } else if (attendance.getId() == null) {

            model.addAttribute(
                    "error",
                    "Attendance has already been registered.");

        } else {

            model.addAttribute(
                    "success",
                    "Attendance registered successfully!");

            model.addAttribute(
                    "studentName",
                    attendance.getStudent().getFirstName()
                            + " "
                            + attendance.getStudent().getLastName());
        }

        return "attendance-register";
    }

    // Historial de asistencias
    @GetMapping("/attendance")
    public String attendanceHistory(Model model) {

        model.addAttribute(
                "attendances",
                attendanceService.getAllAttendances());

        return "attendance";
    }
}
