package org.myschool.repository;

import org.myschool.entity.Classe;
import org.myschool.entity.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {

    Optional<Matiere> findByNom(String nom);

    List<Matiere> findByEstGratuiteTrue();

    List<Matiere> findByClasseId(Long classeId);

}
