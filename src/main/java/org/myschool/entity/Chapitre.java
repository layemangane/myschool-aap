package org.myschool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
public class Chapitre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String titre;

    @Setter
    private boolean estGratuit;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matiere_id")
    private Matiere matiere;

    @Setter
    @OneToMany(mappedBy = "chapitre")
    private Set<Video> videos;

    public Chapitre() {
    }

    public Chapitre(String titre, boolean estGratuit, Matiere matiere ) {
        this.titre = titre;
        this.estGratuit = estGratuit;
        this.matiere = matiere;
    }
}
