package org.myschool.entity;

import jakarta.persistence.Entity;
import org.myschool.enumeration.Role;

@Entity
public class Administrateur extends Utilisateur {

    public Administrateur(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse);
    }

    public Administrateur() {

    }

    @Override
    public Role getRole() {
        return Role.ADMINISTRATEUR;
    }

}
