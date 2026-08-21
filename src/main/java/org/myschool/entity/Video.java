package org.myschool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String titre;

    @Setter
    private int dureeMinutes;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapitre_id")
    private Chapitre chapitre;

    public Video() {}

    public Video(String titre, int dureeMinutes, Chapitre chapitre) {
        this.titre = titre;
        this.dureeMinutes = dureeMinutes;
        this.chapitre = chapitre;
    }

}
