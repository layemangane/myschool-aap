package org.myschool.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SouscriptionRequestDTO {

    @NotNull(message = "Id formule est obligatoire")
    @Positive(message = "L'identifiant de la formule doit être positif")
    private Long formuleId;

    @NotEmpty(message = "Vous devez sélectionner au moins une matière")
    private List<@NotNull(message = "L'identifiant d'une matière est obligatoire") @Positive(message = "L'identifiant d'une matière doit être positif") Long> matiereIds;

}
