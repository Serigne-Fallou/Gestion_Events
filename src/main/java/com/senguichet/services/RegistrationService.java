package com.senguichet.services;

import com.senguichet.entities.Registration;
import com.senguichet.entities.User;
import com.senguichet.entities.Event;
import com.senguichet.repositories.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import com.senguichet.services.EmailService;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private EmailService emailService;

    /**
     * Inscrire un utilisateur à un événement et lui envoyer un e-mail de confirmation.
     */
    public Registration registerUserForEvent(User user, Event event, double amountPaid) {
        // Vérifier si l'utilisateur a un email valide
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("L'utilisateur avec ID " + user.getId() + " n'a pas d'email valide.");
        }

        // Création de l'inscription
        Registration registration = new Registration();
        registration.setUser(user);
        registration.setEvent(event);
        registration.setAmountPaid(amountPaid);
        registration.setPaymentStatus("CONFIRMED");
        registration.setRegistrationDate(LocalDateTime.now());

        // Sauvegarder l'inscription
        Registration savedRegistration = registrationRepository.save(registration);

        // Envoi de l'e-mail de confirmation
        sendRegistrationEmail(user, event);

        return savedRegistration;
    }

    /**
     * Méthode privée pour envoyer un e-mail après l'inscription.
     */
    private void sendRegistrationEmail(User user, Event event) {
        String subject = "Confirmation d'inscription à l'événement : " + event.getName();
        String message = "<h1>Bonjour " + user.getFirstName() + ",</h1>"
                + "<p>Vous êtes bien inscrit à l'événement <strong>" + event.getName() + "</strong>.</p>"
                + "<p>Date de l'événement : <strong>" + event.getDate() + "</strong></p>"
                + "<p>Lieu : <strong>" + event.getLocation() + "</strong></p>"
                + "<p>Montant payé : <strong>" + event.getPrice() + " €</strong></p>"
                + "<br><p>Merci de votre participation !</p>";

        emailService.sendEmail(user.getEmail(), subject, message);
    }
}
