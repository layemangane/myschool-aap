package org.myschool.repository;

import org.myschool.entity.Abonnement;
import org.myschool.entity.AbonnementMatiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbonnementMatiereRepository extends JpaRepository<AbonnementMatiere, Long> {

    List<AbonnementMatiere> findByAbonnement(Abonnement abonnement);

    long countByAbonnement(Abonnement abonnement);
}
