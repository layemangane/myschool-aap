package org.myschool.repository;

import org.myschool.entity.Abonnement;
import org.myschool.entity.Eleve;
import org.myschool.enumeration.StatutAbonnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbonnementRepository extends JpaRepository<Abonnement, Long> {

    List<Abonnement> findByStatus(StatutAbonnement statutAbonnement);

    Optional<Abonnement> findByEleveAndStatus(Eleve eleve, StatutAbonnement status);

}
