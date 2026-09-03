package org.myschool.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.myschool.entity.Abonnement;
import org.myschool.entity.Eleve;
import org.myschool.enumeration.StatutAbonnement;
import org.myschool.exception.BusinessRuleException;
import org.myschool.exception.ResourceNotFoundException;
import org.myschool.repository.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbonnementServiceTest {

    @Mock
    private AbonnementRepository abonnementRepository;
    @Mock
    private AbonnementMatiereRepository abonnementMatiereRepository;
    @Mock
    private EleveRepository eleveRepository;
    @Mock
    private FormuleRepository formuleRepository;
    @Mock
    private MatiereRepository matiereRepository;
    @Mock
    private PaiementRepository paiementRepository;

    @InjectMocks
    private AbonnementService abonnementService;

    @Test
    void createAbonnementRejectsStudentWithActiveSubscription() {
        Eleve eleve = mock(Eleve.class);
        when(eleveRepository.findById(1L)).thenReturn(Optional.of(eleve));
        when(abonnementRepository.findByEleveAndStatus(eleve, StatutAbonnement.ACTIF))
                .thenReturn(Optional.of(new Abonnement()));

        assertThrows(BusinessRuleException.class, () -> abonnementService.createAbonnement(1L, 1L, List.of(1L)));
        verifyNoInteractions(formuleRepository, matiereRepository, abonnementMatiereRepository);
    }

    @Test
    void findAbonnementByIdThrowsWhenSubscriptionDoesNotExist() {
        when(abonnementRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> abonnementService.findAbonnementById(99L));
    }
}
