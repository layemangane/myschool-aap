package org.myschool.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChapitreReqDTO {
    @NotBlank(message = "Le titre du chapitre est obligatoire")
    @Size(max = 200, message = "Le titre du chapitre ne peut pas dépasser 200 caractères")
    private String titre;
    private boolean estGratuit;
}
