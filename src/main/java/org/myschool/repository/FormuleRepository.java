package org.myschool.repository;

import org.myschool.entity.Formule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface FormuleRepository extends JpaRepository<Formule, Long> {

    Optional<Formule> findByNom(String nome);

    List<Formule> findByNombreMatieresMax(Integer nombreMatieresMax);

    List<Formule> findByPrixGreaterThan(BigDecimal prix);

    List<Formule> findByPrixLessThan(BigDecimal prix);
}
