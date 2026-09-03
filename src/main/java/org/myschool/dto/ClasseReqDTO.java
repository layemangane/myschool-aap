package org.myschool.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.myschool.entity.Classe;

@Getter
@Setter
@NoArgsConstructor
public class ClasseReqDTO {

    @NotBlank(message = "Le nom de la classe est obligatoire")
    @Size(max = 100, message = "Le nom de la classe ne peut pas dépasser 100 caractères")
    private String nom;

    @PositiveOrZero(message = "L'ordre de la classe doit être positif ou nul")
    private int ordre;


}
