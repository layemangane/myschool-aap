package org.myschool.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InscriptionDTO {
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String nom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Size(max = 254, message = "L'email ne peut pas dépasser 254 caractères")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, max = 72, message = "Le mot de passe doit contenir entre 8 et 72 caractères")
    private String motDePasse;

    @NotNull(message = "La classe est obligatoire")
    @Positive(message = "L'identifiant de la classe doit être positif")
    private Long classeId;

}
