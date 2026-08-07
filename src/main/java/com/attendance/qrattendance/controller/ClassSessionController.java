package com.attendance.qrattendance.controller;

import com.attendance.qrattendance.model.ClassSession;
import com.attendance.qrattendance.service.ClassSessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClassSessionController {

    private final ClassSessionService classSessionService;

    public ClassSessionController(ClassSessionService classSessionService) {
        this.classSessionService = classSessionService;
    }

    // Mostrar las sesiones y el formulario
    @GetMapping("/classes")
    public String classes(Model model) {

        model.addAttribute("classSession", new ClassSession());
        model.addAttribute("classSessions", classSessionService.getAllClassSessions());

        return "classes";
    }

    // Guardar o actualizar una sesión
    @PostMapping("/classes/save")
    public String saveClassSession(@ModelAttribute ClassSession classSession) {

        classSessionService.saveClassSession(classSession);

        return "redirect:/classes";
    }

    // Editar una sesión
    @GetMapping("/classes/edit/{id}")
    public String editClassSession(@PathVariable Long id, Model model) {

        ClassSession classSession = classSessionService.getClassSessionById(id);

        model.addAttribute("classSession", classSession);
        model.addAttribute("classSessions", classSessionService.getAllClassSessions());

        return "classes";
    }

    // Eliminar una sesión
    @GetMapping("/classes/delete/{id}")
    public String deleteClassSession(@PathVariable Long id) {

        classSessionService.deleteClassSession(id);

        return "redirect:/classes";
    }
}
