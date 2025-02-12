package com.senguichet.repositories;

import com.senguichet.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    // Trouver toutes les inscriptions d'un utilisateur par son ID
    List<Registration> findByUserId(Long userId);

    // Trouver toutes les inscriptions pour un événement par son ID
    List<Registration> findByEventId(Long eventId);
}