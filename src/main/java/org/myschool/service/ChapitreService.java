package org.myschool.service;

import lombok.AllArgsConstructor;
import org.myschool.dto.ChapitreReqDTO;
import org.myschool.entity.Chapitre;
import org.myschool.entity.Matiere;
import org.myschool.exception.ResourceNotFoundException;
import org.myschool.repository.ChapitreRepository;
import org.myschool.repository.MatiereRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ChapitreService {

    private final ChapitreRepository chapitreRepository;
    private final MatiereRepository matiereRepository;

    public Chapitre createChapitre(Long  matiereId, ChapitreReqDTO chapitreReqDTO) {
        Matiere matiere = matiereRepository.findById(matiereId)
                .orElseThrow(() -> new ResourceNotFoundException("Matière introuvable"));
        return chapitreRepository.save(new Chapitre(chapitreReqDTO.getTitre(), chapitreReqDTO.isEstGratuit(), matiere));
    }

    public List<Chapitre> findAllChapitresByMatiereId(Long matiereId) {
        Matiere matiere = matiereRepository.findById(matiereId)
                .orElseThrow(() -> new ResourceNotFoundException("Matière introuvable"));
        return chapitreRepository.findByMatiereId(matiere.getId());
    }

    public Chapitre findChapitreById(Long chapitreId) {
        return chapitreRepository.findById(chapitreId)
                .orElseThrow(() -> new ResourceNotFoundException("Chapitre introuvable"));
    }

    @Transactional
    public Chapitre updateChapitre(Long id, ChapitreReqDTO chapitreReqDTO) {
        Chapitre chapitreFound = chapitreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chapitre introuvable"));
        chapitreFound.setTitre(chapitreReqDTO.getTitre());
        chapitreFound.setEstGratuit(chapitreReqDTO.isEstGratuit());
        return chapitreRepository.save(chapitreFound);
    }

    @Transactional
    public void deleteChapitre(Long id) {
        Chapitre chapitre = findChapitreById(id);
        chapitreRepository.delete(chapitre);
    }

}

