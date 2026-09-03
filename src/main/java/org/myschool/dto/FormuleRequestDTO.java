package org.myschool.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class FormuleRequestDTO {

    @NotBlank(message = "Le nom de la formule est obligatoire")
    @Size(max = 100, message = "Le nom de la formule ne peut pas dépasser 100 caractères")
    private String nom;

    @Positive(message = "Le nombre maximal de matières doit être positif")
    private Integer nombreMatieresMax;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être positif")
    private BigDecimal prix;
}
