package org.myschool.dto;

import lombok.Getter;
import lombok.Setter;
import org.myschool.entity.Eleve;

@Getter
@Setter
public class EleveInscriDTO {
    private String nom;
    private String email;
    private String nomClasse;

    public EleveInscriDTO(Eleve eleve) {
        this.nom = eleve.getNom();
        this.email = eleve.getEmail();
        this.nomClasse = eleve.getClasse().getNom();
    }
}
