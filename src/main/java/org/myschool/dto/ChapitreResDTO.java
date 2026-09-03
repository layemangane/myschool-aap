package org.myschool.dto;

import lombok.Getter;
import lombok.Setter;
import org.myschool.entity.Chapitre;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ChapitreResDTO {
    private Long id;
    private String titre;
    private boolean estGratuit;
    private String nomMatiere;

    public ChapitreResDTO(Chapitre chapitre) {
        this.id = chapitre.getId();
        this.titre = chapitre.getTitre();
        this.estGratuit = chapitre.isEstGratuit();
        this.nomMatiere = chapitre.getMatiere().getNom();
    }
}
