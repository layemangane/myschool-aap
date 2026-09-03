package org.myschool.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.myschool.dto.ClasseReqDTO;
import org.myschool.entity.Classe;
import org.myschool.exception.ResourceNotFoundException;
import org.myschool.repository.ClasseRepository;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClasseServiceTest {

    @Mock
    private ClasseRepository classeRepository;

    @InjectMocks
    private ClasseService classeService;

    @Test
    void createClasseSavesRequestValues() {
        ClasseReqDTO request = new ClasseReqDTO();
        request.setNom("Terminale");
        request.setOrdre(12);
        when(classeRepository.save(any(Classe.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Classe result = classeService.createClasse(request);

        assertEquals("Terminale", result.getNom());
        assertEquals(12, result.getOrdre());
        verify(classeRepository).save(any(Classe.class));
    }

    @Test
    void updateClasseUpdatesExistingClass() {
        Classe existing = new Classe("Sixième", 6);
        ClasseReqDTO request = new ClasseReqDTO();
        request.setNom("Cinquième");
        request.setOrdre(5);
        when(classeRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(classeRepository.save(existing)).thenReturn(existing);

        Classe result = classeService.updateClasse(1L, request);

        assertEquals("Cinquième", result.getNom());
        assertEquals(5, result.getOrdre());
        verify(classeRepository).save(existing);
    }

    @Test
    void findClasseByIdThrowsWhenClassDoesNotExist() {
        when(classeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> classeService.findClasseById(99L));
    }

    @Test
    void deleteClasseDeletesExistingClass() {
        Classe existing = new Classe("Seconde", 10);
        when(classeRepository.findById(1L)).thenReturn(Optional.of(existing));

        classeService.deleteClasse(1L);

        verify(classeRepository).delete(existing);
    }
}
