package org.myschool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
public class Formule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String nom;

    @Setter
    private Integer nombreMatieresMax;

    @Setter
    private BigDecimal prix;

    @OneToMany(mappedBy = "formule")
    private Set<Abonnement> abonnements;

    public Formule() {}

    public Formule(String nom, Integer nombreMatieresMax, BigDecimal prix) {
        this.nom = nom;
        this.nombreMatieresMax = nombreMatieresMax;
        this.prix = prix;
    }
}
