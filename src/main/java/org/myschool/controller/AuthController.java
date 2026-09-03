package org.myschool.controller;

import lombok.AllArgsConstructor;
import jakarta.validation.Valid;
import org.myschool.dto.ConnexionDTO;
import org.myschool.dto.EleveInscriDTO;
import org.myschool.dto.InscriptionDTO;
import org.myschool.dto.ResponseTokenDTO;
import org.myschool.entity.Eleve;
import org.myschool.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/inscription")
    public ResponseEntity<EleveInscriDTO> inscription(@Valid @RequestBody InscriptionDTO inscriptionDTO) {
        Eleve eleve = authService.inscriptionEleve(inscriptionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new EleveInscriDTO(eleve));
    }

    @PostMapping("/connexion")
    public ResponseEntity<ResponseTokenDTO> connexion(@Valid @RequestBody ConnexionDTO connexionDTO) {
        String token = authService.connexion(connexionDTO);
        return ResponseEntity.ok(new ResponseTokenDTO(token));
    }
}
