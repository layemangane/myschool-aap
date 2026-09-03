package org.myschool.security;

import lombok.AllArgsConstructor;
import org.myschool.entity.Eleve;
import org.myschool.exception.ResourceNotFoundException;
import org.myschool.repository.EleveRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CurrentUserProvider {

    private final EleveRepository eleveRepository;

    public Eleve getEleveConnecte() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return eleveRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Élève authentifié introuvable"));
    }
}
