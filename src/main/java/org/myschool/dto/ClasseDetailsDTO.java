package org.myschool.dto;

import lombok.Getter;
import lombok.Setter;
import org.myschool.entity.Classe;
import org.myschool.entity.Matiere;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ClasseDetailsDTO {
    private Long id;
    private String nom;
    private int ordre;
    private int nombreEleves;
    private List<String> matieres;

    public ClasseDetailsDTO(Classe classe) {
        this.id = classe.getId();
        this.nom = classe.getNom();
        this.ordre = classe.getOrdre();
        this.nombreEleves = classe.getEleves().size();
        this.matieres = classe.getMatieres().stream().map(Matiere::getNom).collect(Collectors.toList());
    }
}
