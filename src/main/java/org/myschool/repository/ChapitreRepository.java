package org.myschool.repository;

import org.myschool.entity.Chapitre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChapitreRepository extends JpaRepository<Chapitre, Long> {

    Optional<Chapitre> findByTitre(String titre);

    List<Chapitre> findByEstGratuitTrue();

    List<Chapitre> findByMatiereId(Long matiereId);

}
