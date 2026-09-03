package org.myschool.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.myschool.entity.Classe;

@Getter
@Setter
@NoArgsConstructor
public class ClasseResDTO {

    private Long id;

    private String nom;

    private int ordre;

    public ClasseResDTO(Classe classe){
        this.id = classe.getId();
        this.nom = classe.getNom();
        this.ordre = classe.getOrdre();
    }
}
