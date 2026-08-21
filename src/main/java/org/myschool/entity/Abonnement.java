package org.myschool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.myschool.enumeration.StatutAbonnement;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
public class Abonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eleve_id")
    private Eleve eleve;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formule_id")
    private Formule formule;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    private StatutAbonnement status;

    @Setter
    @OneToMany(mappedBy = "abonnement")
    private List<AbonnementMatiere> abonnementMatieres;

    @OneToOne(mappedBy = "abonnement")
    private Paiement paiement;

    public void activer(LocalDate dateDebut, LocalDate dateFin) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.status = StatutAbonnement.ACTIF;
    }

    public Abonnement() {}

    public Abonnement(Eleve eleve, Formule formule) {
        this.eleve = eleve;
        this.formule = formule;
        this.status = StatutAbonnement.EN_ATTENTE_PAIEMENT;
    }

}
