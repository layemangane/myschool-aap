package org.myschool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.myschool.enumeration.MoyenPaiement;
import org.myschool.enumeration.StatutPaiement;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @Setter
    @JoinColumn(name = "abonnement_id")
    private Abonnement abonnement;

    private BigDecimal montant;

    @Setter
    private LocalDate datePaiement;

    @Setter
    @Enumerated(EnumType.STRING)
    private MoyenPaiement moyenPaiement;


    @Enumerated(EnumType.STRING)
    private StatutPaiement status;

    public Paiement() {}

    public Paiement(Abonnement abonnement, BigDecimal montant, LocalDate datePaiement, MoyenPaiement moyenPaiement, StatutPaiement status) {
        this.abonnement = abonnement;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.moyenPaiement = moyenPaiement;
        this.status = status;
    }

    public void marquerReussi() { this.status = StatutPaiement.REUSSI; }

    public void marquerEchoue() { this.status = StatutPaiement.ECHOUE; }

}
