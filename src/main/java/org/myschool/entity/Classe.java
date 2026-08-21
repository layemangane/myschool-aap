package org.myschool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String nom;

    @Setter
    private int ordre;

    @Setter
    @OneToMany(mappedBy = "classe")
    private Set<Matiere> matieres;

    @Setter
    @OneToMany(mappedBy = "classe")
    private List<Eleve> eleves;

    public Classe() {
    }

    public Classe(String nom, int ordre) {
        this.nom = nom;
        this.ordre = ordre;
    }

}
