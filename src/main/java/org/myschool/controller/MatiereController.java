package org.myschool.controller;

import lombok.AllArgsConstructor;
import jakarta.validation.Valid;
import org.myschool.dto.MatiereRequestDTO;
import org.myschool.dto.MatiereResponseDTO;
import org.myschool.entity.Matiere;
import org.myschool.service.MatiereService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/api")
public class MatiereController {

    private final MatiereService matiereService;

    @PostMapping("/classes/{classeId}/matieres")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<MatiereResponseDTO> createMatiere(@PathVariable Long classeId, @Valid @RequestBody MatiereRequestDTO matiereRequestDTO) {
        Matiere matiere = matiereService.creerMatiere(classeId, matiereRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MatiereResponseDTO(matiere));
    }

    @GetMapping("/classes/{classeId}/matieres")
    public ResponseEntity<List<MatiereResponseDTO>> getMatieres(@PathVariable Long classeId) {
        List<MatiereResponseDTO> matieres = matiereService.listerMatieresParClasse(classeId).stream()
                .map(MatiereResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(matieres);
    }

    @GetMapping("/matieres/{id}")
    public ResponseEntity<MatiereResponseDTO> getMatiereById(@PathVariable Long id) {
        Matiere matiere = matiereService.findMatiereById(id);
        return ResponseEntity.ok(new MatiereResponseDTO(matiere));
    }

    @PutMapping("/matieres/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<MatiereResponseDTO> updateMatiere(@PathVariable Long id, @Valid @RequestBody MatiereRequestDTO matiereRequestDTO) {
        Matiere updatedMatiere = matiereService.modifierMatiere(id, matiereRequestDTO);
        return ResponseEntity.ok(new MatiereResponseDTO(updatedMatiere));
    }

    @DeleteMapping("/matieres/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<String> deleteMatiere(@PathVariable Long id) {
        matiereService.supprimerMatiere(id);
        return ResponseEntity.ok("{\"message\":\"Matiere supprimer !\"}");
    }

}
