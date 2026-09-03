package org.myschool.controller;

import lombok.AllArgsConstructor;
import jakarta.validation.Valid;
import org.myschool.dto.ChapitreReqDTO;
import org.myschool.dto.ChapitreResDTO;
import org.myschool.entity.Chapitre;
import org.myschool.service.ChapitreService;
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
public class ChapitreContoller {

    private final ChapitreService chapitreService;

    @PostMapping("/matieres/{matiereId}/chapitres")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<ChapitreResDTO> createChapitre(@PathVariable Long matiereId, @Valid @RequestBody ChapitreReqDTO chapitreReqDTO) {
        Chapitre createdChapitre = chapitreService.createChapitre(matiereId, chapitreReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ChapitreResDTO(createdChapitre));
    }

    @GetMapping("/matieres/{matiereId}/chapitres")
    public ResponseEntity<List<ChapitreResDTO>> getAllChapitres(@PathVariable Long matiereId) {
        List<ChapitreResDTO> chapitres = chapitreService.findAllChapitresByMatiereId(matiereId).stream()
                .map(ChapitreResDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(chapitres);
    }

    @GetMapping("/chapitres/{id}")
    public ResponseEntity<ChapitreResDTO> getChapitreById(@PathVariable Long id) {
        Chapitre chapitre = chapitreService.findChapitreById(id);
        return ResponseEntity.ok(new ChapitreResDTO(chapitre));
    }

    @PutMapping("/chapitres/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<ChapitreResDTO> updateChapitre(@PathVariable Long id, @Valid @RequestBody ChapitreReqDTO chapitreReqDTO) {
        Chapitre chapitreToUpdate = chapitreService.updateChapitre(id, chapitreReqDTO);
        return ResponseEntity.ok(new ChapitreResDTO(chapitreToUpdate));
    }

    @DeleteMapping("/chapitres/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Void> deleteChapitre(@PathVariable Long id) {
        chapitreService.deleteChapitre(id);
        return ResponseEntity.noContent().build();
    }

}
