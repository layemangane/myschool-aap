package org.myschool.dto;

import lombok.Getter;
import lombok.Setter;
import org.myschool.entity.Formule;

import java.math.BigDecimal;

@Getter
@Setter
public class FormuleResDTO {
    private Long id;
    private String nom;
    private Integer nombreMatieresMax;
    private BigDecimal prix;

    public FormuleResDTO(Formule formule){
        this.id = formule.getId();
        this.nom = formule.getNom();
        this.nombreMatieresMax = formule.getNombreMatieresMax();
        this.prix = formule.getPrix();
    }
}
