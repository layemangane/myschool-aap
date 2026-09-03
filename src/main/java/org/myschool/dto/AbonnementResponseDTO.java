package org.myschool.dto;

import lombok.Getter;
import lombok.Setter;
import org.myschool.entity.Abonnement;
import org.myschool.enumeration.StatutAbonnement;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AbonnementResponseDTO {

    private Long id;

    private String nomFormule;

    private StatutAbonnement status;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private List<String> matieres;

    public AbonnementResponseDTO(Abonnement abonnement) {
        this.id = abonnement.getId();
        this.nomFormule = abonnement.getFormule().getNom();
        this.status = abonnement.getStatus();
        this.dateDebut = abonnement.getDateDebut();
        this.dateFin = abonnement.getDateFin();
        this.matieres = abonnement.getAbonnementMatieres().stream()
                .map(abm -> abm.getMatiere().getNom())
                .collect(Collectors.toUnmodifiableList());
    }
}
