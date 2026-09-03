package org.myschool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Matiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @OneToMany(mappedBy = "matiere")
    private List<Chapitre> chapitres;

    @OneToMany(mappedBy = "matiere")
    private List<AbonnementMatiere> abonnementMatieres;

    private boolean estGratuite;

    private boolean active = true;

    public Matiere() {
    }

    public Matiere(String nom, Classe classe, boolean estGratuite) {
        this.nom = nom;
        this.classe = classe;
        this.estGratuite = estGratuite;
    }

}
