package org.myschool.service;

import lombok.AllArgsConstructor;
import org.myschool.dto.ConnexionDTO;
import org.myschool.dto.InscriptionDTO;
import org.myschool.entity.Classe;
import org.myschool.entity.Eleve;
import org.myschool.entity.Utilisateur;
import org.myschool.exception.BusinessRuleException;
import org.myschool.exception.InvalidCredentialsException;
import org.myschool.exception.ResourceNotFoundException;
import org.myschool.repository.ClasseRepository;
import org.myschool.repository.EleveRepository;
import org.myschool.repository.UtilisateurRepository;
import org.myschool.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final EleveRepository eleveRepository;
    private final ClasseRepository classeRepository;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurRepository utilisateurRepository;
    private final JwtService jwtService;

    public Eleve inscriptionEleve(InscriptionDTO inscriptionDTO){
        if (eleveRepository.existsByEmail(inscriptionDTO.getEmail())){
            throw new BusinessRuleException("Un élève utilise déjà cet email");
        }

        Classe classe = classeRepository.findById(inscriptionDTO.getClasseId())
                .orElseThrow(() -> new ResourceNotFoundException("Classe introuvable"));

        String hachedPassword = passwordEncoder.encode(inscriptionDTO.getMotDePasse());
        Eleve eleve = new Eleve(
                inscriptionDTO.getNom(),
                inscriptionDTO.getEmail(),
                hachedPassword,
                classe
        );
        return eleveRepository.save(eleve);
    }

    public String connexion(ConnexionDTO connexionDTO){
        Utilisateur user = utilisateurRepository.findByEmail(connexionDTO.getEmail())
                .orElseThrow(InvalidCredentialsException::new);
        if (!passwordEncoder.matches(connexionDTO.getMotDePasse(), user.getMotDePasse())){
            throw new InvalidCredentialsException();
        }
        return jwtService.generateToken(user.getEmail(), user.getRole());
    }
}
