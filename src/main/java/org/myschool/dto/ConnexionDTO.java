package org.myschool.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConnexionDTO {
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Size(max = 254, message = "L'email ne peut pas dépasser 254 caractères")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;
}
