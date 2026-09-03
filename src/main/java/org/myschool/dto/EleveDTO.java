package org.myschool.dto;

import lombok.Getter;
import lombok.Setter;
import org.myschool.entity.Eleve;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class EleveDTO {
    private Long id;
    private String nom;
    private String email;
    private String nomClasse;
    private List<AbonnementResponseDTO> abonnements;

    public EleveDTO(Eleve eleve) {
        this.id = eleve.getId();
        this.nom = eleve.getNom();
        this.email = eleve.getEmail();
        this.nomClasse = eleve.getClasse().getNom();
        this.abonnements = eleve.getAbonnements().stream()
                .map(AbonnementResponseDTO::new).collect(Collectors.toList());
    }
}
