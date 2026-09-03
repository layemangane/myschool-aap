package org.myschool.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.myschool.dto.ConnexionDTO;
import org.myschool.dto.InscriptionDTO;
import org.myschool.entity.Classe;
import org.myschool.entity.Eleve;
import org.myschool.entity.Utilisateur;
import org.myschool.enumeration.Role;
import org.myschool.exception.BusinessRuleException;
import org.myschool.exception.InvalidCredentialsException;
import org.myschool.repository.ClasseRepository;
import org.myschool.repository.EleveRepository;
import org.myschool.repository.UtilisateurRepository;
import org.myschool.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private EleveRepository eleveRepository;
    @Mock
    private ClasseRepository classeRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UtilisateurRepository utilisateurRepository;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void inscriptionEleveHashesPasswordAndSavesStudent() {
        InscriptionDTO request = inscription();
        Classe classe = new Classe("Terminale", 12);
        when(eleveRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(classeRepository.findById(1L)).thenReturn(Optional.of(classe));
        when(passwordEncoder.encode("motdepasse")).thenReturn("hashé");
        when(eleveRepository.save(any(Eleve.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Eleve result = authService.inscriptionEleve(request);

        assertEquals("Awa Ndiaye", result.getNom());
        assertEquals("awa@example.com", result.getEmail());
        assertEquals("hashé", result.getMotDePasse());
        assertSame(classe, result.getClasse());
        verify(passwordEncoder).encode("motdepasse");
    }

    @Test
    void inscriptionEleveRejectsExistingEmail() {
        InscriptionDTO request = inscription();
        when(eleveRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(BusinessRuleException.class, () -> authService.inscriptionEleve(request));
        verifyNoInteractions(classeRepository, passwordEncoder);
    }

    @Test
    void connexionReturnsJwtForValidCredentials() {
        ConnexionDTO request = new ConnexionDTO();
        request.setEmail("awa@example.com");
        request.setMotDePasse("motdepasse");
        Utilisateur user = mock(Utilisateur.class);
        when(utilisateurRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(user.getMotDePasse()).thenReturn("hashé");
        when(user.getEmail()).thenReturn(request.getEmail());
        when(user.getRole()).thenReturn(Role.ELEVE);
        when(passwordEncoder.matches("motdepasse", "hashé")).thenReturn(true);
        when(jwtService.generateToken(request.getEmail(), Role.ELEVE)).thenReturn("jwt-token");

        String token = authService.connexion(request);

        assertEquals("jwt-token", token);
        verify(jwtService).generateToken(request.getEmail(), Role.ELEVE);
    }

    @Test
    void connexionRejectsInvalidPassword() {
        ConnexionDTO request = new ConnexionDTO();
        request.setEmail("awa@example.com");
        request.setMotDePasse("incorrect");
        Utilisateur user = mock(Utilisateur.class);
        when(utilisateurRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(user.getMotDePasse()).thenReturn("hashé");
        when(passwordEncoder.matches("incorrect", "hashé")).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> authService.connexion(request));
        verifyNoInteractions(jwtService);
    }

    private InscriptionDTO inscription() {
        InscriptionDTO request = new InscriptionDTO();
        request.setNom("Awa Ndiaye");
        request.setEmail("awa@example.com");
        request.setMotDePasse("motdepasse");
        request.setClasseId(1L);
        return request;
    }
}
