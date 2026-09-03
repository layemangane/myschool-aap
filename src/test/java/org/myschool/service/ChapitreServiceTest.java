package org.myschool.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.myschool.dto.ChapitreReqDTO;
import org.myschool.entity.Chapitre;
import org.myschool.entity.Matiere;
import org.myschool.exception.ResourceNotFoundException;
import org.myschool.repository.ChapitreRepository;
import org.myschool.repository.MatiereRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChapitreServiceTest {

    @Mock
    private ChapitreRepository chapitreRepository;

    @Mock
    private MatiereRepository matiereRepository;

    @InjectMocks
    private ChapitreService chapitreService;

    @Test
    void createChapitreSavesChapterForSubject() {
        Matiere matiere = new Matiere();
        ChapitreReqDTO request = request("Les équations", true);
        when(matiereRepository.findById(1L)).thenReturn(Optional.of(matiere));
        when(chapitreRepository.save(any(Chapitre.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Chapitre result = chapitreService.createChapitre(1L, request);

        assertEquals("Les équations", result.getTitre());
        assertTrue(result.isEstGratuit());
        assertSame(matiere, result.getMatiere());
    }

    @Test
    void updateChapitreThrowsWhenChapterDoesNotExist() {
        when(chapitreRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> chapitreService.updateChapitre(99L, request("Nouveau", false)));
    }

    @Test
    void deleteChapitreDeletesExistingChapter() {
        Chapitre chapitre = new Chapitre();
        when(chapitreRepository.findById(1L)).thenReturn(Optional.of(chapitre));

        chapitreService.deleteChapitre(1L);

        verify(chapitreRepository).delete(chapitre);
    }

    private ChapitreReqDTO request(String titre, boolean gratuit) {
        ChapitreReqDTO request = new ChapitreReqDTO();
        request.setTitre(titre);
        request.setEstGratuit(gratuit);
        return request;
    }
}
