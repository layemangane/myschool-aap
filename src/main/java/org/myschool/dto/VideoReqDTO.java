package org.myschool.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VideoReqDTO {
    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 200, message = "Le titre ne peut pas dépasser 200 caractères")
    private String titre;

    @Min(value = 1, message = "La durée doit être d'au moins une minute")
    @Max(value = 15, message = "Une vidéo ne peut pas dépasser 15 minutes")
    private int dureeMinutes;
}

