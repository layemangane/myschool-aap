package org.myschool.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.myschool.dto.MatiereRequestDTO;
import org.myschool.entity.Classe;
import org.myschool.entity.Matiere;
import org.myschool.exception.ResourceNotFoundException;
import org.myschool.repository.ClasseRepository;
import org.myschool.repository.MatiereRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatiereServiceTest {

    @Mock
    private MatiereRepository matiereRepository;

    @Mock
    private ClasseRepository classeRepository;

    @InjectMocks
    private MatiereService matiereService;

    @Test
    void creerMatiereAttachesSubjectToClass() {
        Classe classe = new Classe("Troisième", 3);
        MatiereRequestDTO request = request("Mathématiques", false);
        when(classeRepository.findById(3L)).thenReturn(Optional.of(classe));
        when(matiereRepository.save(any(Matiere.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Matiere result = matiereService.creerMatiere(3L, request);

        assertEquals("Mathématiques", result.getNom());
        assertSame(classe, result.getClasse());
        assertFalse(result.isEstGratuite());
    }

    @Test
    void listerMatieresParClasseExcludesInactiveSubjects() {
        Matiere active = new Matiere("Français", new Classe(), true);
        Matiere inactive = new Matiere("Sciences", new Classe(), false);
        inactive.setActive(false);
        when(matiereRepository.findByClasseId(1L)).thenReturn(List.of(active, inactive));

        List<Matiere> result = matiereService.listerMatieresParClasse(1L);

        assertEquals(List.of(active), result);
    }

    @Test
    void findMatiereByIdThrowsForInactiveSubject() {
        Matiere inactive = new Matiere("Sciences", new Classe(), false);
        inactive.setActive(false);
        when(matiereRepository.findById(1L)).thenReturn(Optional.of(inactive));

        assertThrows(ResourceNotFoundException.class, () -> matiereService.findMatiereById(1L));
    }

    @Test
    void supprimerMatiereMarksSubjectInactive() {
        Matiere matiere = new Matiere("Histoire", new Classe(), false);
        when(matiereRepository.findById(1L)).thenReturn(Optional.of(matiere));

        matiereService.supprimerMatiere(1L);

        assertFalse(matiere.isActive());
        verify(matiereRepository, never()).delete(any());
    }

    private MatiereRequestDTO request(String nom, boolean gratuite) {
        MatiereRequestDTO request = new MatiereRequestDTO();
        request.setNom(nom);
        request.setEstGratuite(gratuite);
        return request;
    }
}
