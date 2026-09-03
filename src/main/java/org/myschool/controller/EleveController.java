package org.myschool.controller;

import lombok.AllArgsConstructor;
import org.myschool.dto.EleveDTO;
import org.myschool.entity.Eleve;
import org.myschool.service.AbonnementService;
import org.myschool.service.EleveService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/api/eleves")
public class EleveController {

    private final EleveService eleveService;
    private final AbonnementService abonnementService;

    @GetMapping
    public ResponseEntity<List<EleveDTO>> getAllEleves() {
        List<EleveDTO> eleves = eleveService.findAllEleves().stream()
                .map(EleveDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eleves);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EleveDTO> getEleve(@PathVariable Long id){
        Eleve eleve = abonnementService.findEleveById(id);
        return ResponseEntity.ok(new EleveDTO(eleve));
    }

}
