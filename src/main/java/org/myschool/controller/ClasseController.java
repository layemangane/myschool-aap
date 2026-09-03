package org.myschool.controller;

import lombok.AllArgsConstructor;
import jakarta.validation.Valid;
import org.myschool.dto.ClasseDetailsDTO;
import org.myschool.dto.ClasseReqDTO;
import org.myschool.dto.ClasseResDTO;
import org.myschool.entity.Classe;
import org.myschool.service.ClasseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/api/classes")
public class ClasseController {

    private final ClasseService classeService;

    @GetMapping
    public ResponseEntity<List<ClasseResDTO>> findAllClasses(){
        List<ClasseResDTO> classeResDTOS = classeService.findAllClasses().stream()
                .map(ClasseResDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(classeResDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClasseDetailsDTO> findClasseById(@PathVariable Long id){
        Classe classe = classeService.findClasseById(id);
        return ResponseEntity.ok(new ClasseDetailsDTO(classe));
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<ClasseResDTO> createClasse(@Valid @RequestBody ClasseReqDTO dto){
        Classe classe = classeService.createClasse(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ClasseResDTO(classe));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<ClasseResDTO> updateClasse(@PathVariable Long id, @Valid @RequestBody ClasseReqDTO dto) {
        Classe classe = classeService.updateClasse(id, dto);
        return ResponseEntity.ok(new ClasseResDTO(classe));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Void> deleteClasse(@PathVariable Long id) {
        classeService.deleteClasse(id);
        return ResponseEntity.noContent().build();
    }
}
