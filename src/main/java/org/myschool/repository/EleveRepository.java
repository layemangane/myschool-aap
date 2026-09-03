package org.myschool.repository;

import org.myschool.entity.Eleve;
import org.myschool.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EleveRepository extends JpaRepository<Eleve, Long> {
    Optional<Eleve> findByEmail(String email);

    List<Eleve> findByRole(Role role);

    boolean existsByEmail(String email);
}
