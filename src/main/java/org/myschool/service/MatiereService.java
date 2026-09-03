package org.myschool.service;

import lombok.AllArgsConstructor;
import org.myschool.dto.MatiereRequestDTO;
import org.myschool.entity.Classe;
import org.myschool.entity.Matiere;
import org.myschool.exception.ResourceNotFoundException;
import org.myschool.repository.ClasseRepository;
import org.myschool.repository.MatiereRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MatiereService {

    private final MatiereRepository matiereRepository;
    private final ClasseRepository classeRepository;

    public Matiere creerMatiere(Long classeId, MatiereRequestDTO dto) {
        Classe classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new ResourceNotFoundException("Classe introuvable"));
        return matiereRepository.save(new Matiere(dto.getNom(), classe, dto.isEstGratuite()));
    }

    public List<Matiere> listerMatieresParClasse(Long classeId) {
        return matiereRepository.findByClasseId(classeId).stream()
                .filter(Matiere::isActive)
                .collect(Collectors.toList());
    }

    public Matiere findMatiereById(Long id) {
        return matiereRepository.findById(id)
                .filter(Matiere::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Matière introuvable"));
    }

    @Transactional
    public Matiere modifierMatiere(Long id, MatiereRequestDTO matiereRequestDTO) {
        Matiere matiereFound = matiereRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matière introuvable"));
        matiereFound.setNom(matiereRequestDTO.getNom());
        matiereFound.setEstGratuite(matiereRequestDTO.isEstGratuite());
        return matiereRepository.save(matiereFound);
    }

    @Transactional
    public void supprimerMatiere(Long id) {
        Matiere matiere = matiereRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Matière introuvable"));
        matiere.setActive(false);
    }
}
