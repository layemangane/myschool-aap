package org.myschool.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.myschool.dto.FormuleRequestDTO;
import org.myschool.entity.Formule;
import org.myschool.exception.ResourceNotFoundException;
import org.myschool.repository.FormuleRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FormuleServiceTest {

    @Mock
    private FormuleRepository formuleRepository;

    @InjectMocks
    private FormuleService formuleService;

    @Test
    void createFormuleBuildsAndSavesFormula() {
        FormuleRequestDTO request = request("Premium", 4, "15000");
        when(formuleRepository.save(any(Formule.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Formule result = formuleService.createFormule(request);

        assertEquals("Premium", result.getNom());
        assertEquals(4, result.getNombreMatieresMax());
        assertEquals(new BigDecimal("15000"), result.getPrix());
    }

    @Test
    void updateFormuleUpdatesExistingFormula() {
        Formule existing = new Formule("Standard", 2, new BigDecimal("5000"));
        FormuleRequestDTO request = request("Premium", 4, "15000");
        when(formuleRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(formuleRepository.save(existing)).thenReturn(existing);

        Formule result = formuleService.updateFormule(1L, request);

        assertEquals("Premium", result.getNom());
        assertEquals(4, result.getNombreMatieresMax());
        assertEquals(new BigDecimal("15000"), result.getPrix());
    }

    @Test
    void getFormuleByIdThrowsWhenFormulaDoesNotExist() {
        when(formuleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> formuleService.getFormuleById(99L));
    }

    private FormuleRequestDTO request(String nom, int maximum, String prix) {
        FormuleRequestDTO request = new FormuleRequestDTO();
        request.setNom(nom);
        request.setNombreMatieresMax(maximum);
        request.setPrix(new BigDecimal(prix));
        return request;
    }
}
