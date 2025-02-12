package com.senguichet.controllers;

import com.senguichet.entities.Registration;
import com.senguichet.entities.User;
import com.senguichet.entities.Event;
import com.senguichet.services.RegistrationService;
import com.senguichet.repositories.UserRepository;
import com.senguichet.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    private static final Logger logger = Logger.getLogger(RegistrationController.class.getName());

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    /**
     * Inscription d'un utilisateur à un événement via un JSON en `@RequestBody`
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUserForEvent(@RequestBody Registration registrationRequest) {
        try {
            // Vérifier si l'utilisateur existe
            Optional<User> userOpt = userRepository.findById(registrationRequest.getUser().getId());
            if (userOpt.isEmpty()) {
                logger.warning("Utilisateur non trouvé avec ID: " + registrationRequest.getUser().getId());
                return ResponseEntity.badRequest().body("Erreur : Utilisateur non trouvé.");
            }

            // Vérifier si l'événement existe
            Optional<Event> eventOpt = eventRepository.findById(registrationRequest.getEvent().getId());
            if (eventOpt.isEmpty()) {
                logger.warning("Événement non trouvé avec ID: " + registrationRequest.getEvent().getId());
                return ResponseEntity.badRequest().body("Erreur : Événement non trouvé.");
            }

            User user = userOpt.get();
            Event event = eventOpt.get();

            // Inscription et envoi de l'email
            Registration registration = registrationService.registerUserForEvent(user, event, registrationRequest.getAmountPaid());
            logger.info("Inscription réussie pour " + user.getFirstName() + " à l'événement " + event.getName());

            return ResponseEntity.ok("Inscription réussie pour " + user.getFirstName() + " à l'événement " + event.getName());

        } catch (Exception e) {
            logger.severe("Erreur lors de l'inscription : " + e.getMessage());
            return ResponseEntity.internalServerError().body("Erreur interne lors de l'inscription.");
        }
    }
}
