package org.myschool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "abonnement_matiere", uniqueConstraints = @UniqueConstraint(columnNames = {"abonnement_id", "matiere_id"}))
public class AbonnementMatiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "abonnement_id")
    private Abonnement abonnement;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matiere_id")
    private Matiere matiere;

    public AbonnementMatiere() {}

    public AbonnementMatiere(Abonnement abonnement, Matiere matiere) {
        this.abonnement = abonnement;
        this.matiere = matiere;
    }
}
