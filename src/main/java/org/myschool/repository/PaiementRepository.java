package org.myschool.repository;

import org.myschool.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    List<Paiement> findByMontantEquals(BigDecimal montant);

    List<Paiement> findByMontantGreaterThan(BigDecimal montant);

    List<Paiement> findByMontantLessThan(BigDecimal prix);
}
