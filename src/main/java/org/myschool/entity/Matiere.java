package org.myschool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
public class Matiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String nom;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @Setter
    @OneToMany(mappedBy = "matiere")
    private List<Chapitre> chapitres;

    @Setter
    @OneToMany(mappedBy = "matiere")
    private List<AbonnementMatiere> abonnementMatieres;

    @Setter
    private boolean estGratuite;

    public Matiere() {
    }

    public Matiere(String nom, Classe classe, boolean estGratuite) {
        this.nom = nom;
        this.classe = classe;
        this.estGratuite = estGratuite;
    }

}
