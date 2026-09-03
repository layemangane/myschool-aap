package org.myschool.controller;

import lombok.AllArgsConstructor;
import jakarta.validation.Valid;
import org.myschool.dto.FormuleRequestDTO;
import org.myschool.dto.FormuleResDTO;
import org.myschool.entity.Formule;
import org.myschool.service.FormuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/api/formules")
public class FormuleController {

    private final FormuleService formuleService;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<FormuleResDTO> addFormule(@Valid @RequestBody FormuleRequestDTO formule){
        Formule createdFormule = formuleService.createFormule(formule);
        return ResponseEntity.status(HttpStatus.CREATED).body(new FormuleResDTO(createdFormule));
    }

    @GetMapping
    public ResponseEntity<List<FormuleResDTO>> getAllFormules(){
        List<FormuleResDTO> formules = formuleService.getAllFormules().stream()
                .map(FormuleResDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body((formules));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormuleResDTO> getFormuleById(@PathVariable Long id){
        Formule formule = formuleService.getFormuleById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new FormuleResDTO(formule));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<FormuleResDTO> updateFormule(@PathVariable Long id, @Valid @RequestBody FormuleRequestDTO formule){
        Formule updatedFormule = formuleService.updateFormule(id, formule);
        return ResponseEntity.ok(new FormuleResDTO(updatedFormule));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Void> deleteFormule(@PathVariable Long id) {
        formuleService.deleteFormule(id);
        return ResponseEntity.noContent().build();
    }
}
