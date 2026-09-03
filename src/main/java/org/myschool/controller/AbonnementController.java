package org.myschool.controller;

import lombok.AllArgsConstructor;
import jakarta.validation.Valid;
import org.myschool.dto.AbonnementResponseDTO;
import org.myschool.dto.ConfirmationPaiementRequestDTO;
import org.myschool.dto.SouscriptionRequestDTO;
import org.myschool.entity.Abonnement;
import org.myschool.security.CurrentUserProvider;
import org.myschool.service.AbonnementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/abonnements")
public class AbonnementController {

    private final AbonnementService abonnementService;
    private final CurrentUserProvider currentUserProvider;

    @PostMapping
    @PreAuthorize("hasRole('ELEVE')")
    public ResponseEntity<AbonnementResponseDTO> createAbonnement(@Valid @RequestBody SouscriptionRequestDTO souscription){
        Long eleveId = currentUserProvider.getEleveConnecte().getId();
        Abonnement abonnement = abonnementService.createAbonnement(eleveId, souscription.getFormuleId(), souscription.getMatiereIds());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AbonnementResponseDTO(abonnement));
    }

    @PostMapping("/{id}/confirmer-paiement")
    public ResponseEntity<AbonnementResponseDTO> confirmerPaiement(@PathVariable Long id, @Valid @RequestBody ConfirmationPaiementRequestDTO confirmation){
        Abonnement abonnement = abonnementService.confirmerPaiement(id, confirmation.getMontantPaiement(), confirmation.getMoyenPaiement(), confirmation.isPaiementReussi());
        return ResponseEntity.ok(new AbonnementResponseDTO(abonnement));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbonnementResponseDTO> getAbonnement(@PathVariable Long id){
        Abonnement abonnementTrouve = abonnementService.findAbonnementById(id);
        return ResponseEntity.ok(new AbonnementResponseDTO(abonnementTrouve));
    }

}
