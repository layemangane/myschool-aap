package org.myschool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.myschool.enumeration.Role;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Setter
    protected String nom;

    @Setter
    @Column(unique = true, nullable = false)
    protected String email;

    @Setter
    @Column(nullable = false)
    protected String motDePasse;

    protected Utilisateur(String nom, String email, String motDePasse) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public Utilisateur() {

    }

    public abstract Role getRole();

}
