package org.myschool.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.myschool.enumeration.MoyenPaiement;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ConfirmationPaiementRequestDTO {

    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit être positif")
    private BigDecimal montantPaiement;

    @NotNull(message = "Le moyen de paiement est obligatoire")
    private MoyenPaiement moyenPaiement;

    private boolean paiementReussi;

}
