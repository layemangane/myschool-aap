package org.myschool.dto;

import lombok.Getter;
import lombok.Setter;
import org.myschool.entity.Matiere;

@Getter
@Setter
public class MatiereResponseDTO {

    private Long id;

    private String nom;

    private boolean estGratuite;

    private String nomClasse;

    public MatiereResponseDTO(Matiere matiere){
        this.id = matiere.getId();
        this.nom = matiere.getNom();
        this.estGratuite = matiere.isEstGratuite();
        this.nomClasse = matiere.getClasse().getNom();
    }
}
