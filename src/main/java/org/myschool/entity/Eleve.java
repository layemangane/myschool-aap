package org.myschool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.myschool.enumeration.Role;

import java.util.Set;

@Entity
@Getter
public class Eleve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String nom;

    @Setter
    @Column(unique = true, nullable = false)
    private String email;

    @Setter
    @Column(nullable = false)
    private String motDePasse;

    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @Setter
    @OneToMany(mappedBy = "eleve")
    private Set<Abonnement> abonnements;

    public Eleve() {
    }

    public Eleve(String nom, String email, Classe classe, String motDePasse, Role role) {
        this.nom = nom;
        this.email = email;
        this.classe = classe;
        this.motDePasse = motDePasse;
        this.role = role;
    }
}
