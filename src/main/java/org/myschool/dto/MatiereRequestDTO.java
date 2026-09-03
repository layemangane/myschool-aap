package org.myschool.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MatiereRequestDTO {

    @NotBlank(message = "Le nom de la matiere est obligatoire")
    @Size(max = 100, message = "Le nom de la matiere ne peut pas dépasser 100 caractères")
    private String nom;

    private boolean estGratuite;
}
