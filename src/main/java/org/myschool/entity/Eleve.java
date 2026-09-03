package org.myschool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.myschool.enumeration.Role;

import java.util.Set;

@Entity
@Getter
public class Eleve extends Utilisateur {

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @Setter
    @OneToMany(mappedBy = "eleve")
    private Set<Abonnement> abonnements;

    public Eleve(String nom, String email, String motDePasse, Classe classe) {
        super(nom, email, motDePasse);
        this.classe = classe;
    }

    public Eleve() {

    }

    @Override
    public Role getRole() {
        return Role.ELEVE;
    }
}
