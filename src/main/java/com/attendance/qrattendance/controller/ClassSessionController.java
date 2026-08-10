package com.attendance.qrattendance.controller;

import com.attendance.qrattendance.model.ClassSession;
import com.attendance.qrattendance.service.ClassSessionService;
import com.attendance.qrattendance.service.QrCodeService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClassSessionController {

    private final QrCodeService qrCodeService;
    private final ClassSessionService classSessionService;

    public ClassSessionController(
            ClassSessionService classSessionService,
            QrCodeService qrCodeService) {

        this.classSessionService = classSessionService;
        this.qrCodeService = qrCodeService;
    }

    // Mostrar las sesiones y el formulario
    @GetMapping("/classes")
    public String classes(Model model) {

        model.addAttribute("classSession", new ClassSession());
        model.addAttribute(
                "classSessions",
                classSessionService.getAllClassSessions()
        );

        return "classes";
    }

    // Guardar o actualizar una sesión
    @PostMapping("/classes/save")
    public String saveClassSession(
            @ModelAttribute ClassSession classSession,
            RedirectAttributes redirectAttributes) {

        boolean editing = classSession.getId() != null;

        classSessionService.saveClassSession(classSession);

        if (editing) {

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Class session updated successfully!"
            );

        } else {

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Class session created successfully!"
            );
        }

        return "redirect:/classes";
    }

    // Editar una sesión
    @GetMapping("/classes/edit/{id}")
    public String editClassSession(
            @PathVariable Long id,
            Model model) {

        ClassSession classSession =
                classSessionService.getClassSessionById(id);

        model.addAttribute(
                "classSession",
                classSession
        );

        model.addAttribute(
                "classSessions",
                classSessionService.getAllClassSessions()
        );

        return "classes";
    }

    // Eliminar una sesión
    @GetMapping("/classes/delete/{id}")
    public String deleteClassSession(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        classSessionService.deleteClassSession(id);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Class session deleted successfully!"
        );

        return "redirect:/classes";
    }

    // Mostrar página del QR
    @GetMapping("/classes/qr/{id}")
    public String showQrCode(
            @PathVariable Long id,
            Model model) {

        ClassSession classSession =
                classSessionService.getClassSessionById(id);

        if (classSession == null) {
            return "redirect:/classes";
        }

        model.addAttribute(
                "classSession",
                classSession
        );

        return "qr";
    }

    // Generar imagen QR
    @GetMapping("/classes/qr/image/{id}")
    public ResponseEntity<byte[]> generateQrImage(
            @PathVariable Long id) {

        try {

            ClassSession classSession =
                    classSessionService.getClassSessionById(id);

            if (classSession == null) {
                return ResponseEntity.notFound().build();
            }

            // URL PUBLICA DE RAILWAY
            String attendanceUrl =
                    "https://attendance-qr-production-5700.up.railway.app"
                    + "/attendance/register/"
                    + classSession.getId();

            byte[] qrImage =
                    qrCodeService.generateQrCode(
                            attendanceUrl,
                            300,
                            300
                    );

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrImage);

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
}
