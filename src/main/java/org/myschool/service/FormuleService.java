package org.myschool.service;

import lombok.AllArgsConstructor;
import org.myschool.dto.FormuleRequestDTO;
import org.myschool.entity.Formule;
import org.myschool.exception.ResourceNotFoundException;
import org.myschool.repository.FormuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class FormuleService {

    private final FormuleRepository formuleRepository;

    public Formule createFormule(FormuleRequestDTO formule) {
        return formuleRepository.save(new Formule(
                formule.getNom(),
                formule.getNombreMatieresMax(),
                formule.getPrix()
        ));
    }

    public Formule getFormuleById(Long id) {
        return formuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Formule introuvable"));
    }

    public List<Formule> getAllFormules() {
        return formuleRepository.findAll();
    }

    @Transactional
    public Formule updateFormule(Long id, FormuleRequestDTO formule) {
        Formule formuleFound = getFormuleById(id);
        formuleFound.setNom(formule.getNom());
        formuleFound.setNombreMatieresMax(formule.getNombreMatieresMax());
        formuleFound.setPrix(formule.getPrix());
        return formuleRepository.save(formuleFound);
    }

    @Transactional
    public void deleteFormule(Long id) {
        Formule formule = getFormuleById(id);
        formuleRepository.delete(formule);
    }
}
