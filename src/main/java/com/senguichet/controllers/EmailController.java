package com.senguichet.controllers;

import com.senguichet.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    /**
     * Endpoint pour envoyer un e-mail simple.
     */
    @GetMapping("/send")
    public String sendSimpleEmail(
        @RequestParam String to,
        @RequestParam String subject,
        @RequestParam String text
    ) {
        emailService.sendEmail(to, subject, text);
        return "E-mail envoyé à " + to;
    }
}