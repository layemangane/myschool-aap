package org.myschool.service;

import org.myschool.entity.*;
import org.myschool.exception.BusinessRuleException;
import org.myschool.exception.ResourceNotFoundException;
import org.myschool.enumeration.MoyenPaiement;
import org.myschool.enumeration.StatutAbonnement;
import org.myschool.enumeration.StatutPaiement;
import org.myschool.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbonnementService {

    private final AbonnementRepository abonnementRepository;
    private final AbonnementMatiereRepository abonnementMatiereRepository;
    private final EleveRepository eleveRepository;
    private final FormuleRepository formuleRepository;
    private final MatiereRepository matiereRepository;
    private final PaiementRepository paiementRepository;


    public AbonnementService(AbonnementRepository abonnementRepository, AbonnementMatiereRepository abonnementMatiereRepository, EleveRepository eleveRepository, FormuleRepository formuleRepository, MatiereRepository matiereRepository, PaiementRepository paiementRepository) {
        this.abonnementRepository = abonnementRepository;
        this.abonnementMatiereRepository = abonnementMatiereRepository;
        this.eleveRepository = eleveRepository;
        this.formuleRepository = formuleRepository;
        this.matiereRepository = matiereRepository;
        this.paiementRepository = paiementRepository;
    }


    public Abonnement findAbonnementById(Long id) {
        return abonnementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Abonnement introuvable"));
    }


    @Transactional
    public Abonnement createAbonnement(Long eleveId, Long formuleId, List<Long> matiereIds) {
        Eleve eleve = findEleveById(eleveId);

        verifierAbscenceAbonnementActif(eleve);

        Formule formule = findFormuleById(formuleId);

        List<Matiere> matieres = getEtValiderMatieres(matiereIds, eleve);

        verifierNombreMaxDeMatieres(formule, matieres.size());

        Abonnement abonnement = abonnementRepository.save(new Abonnement(eleve, formule));

        matieres.forEach(matiere -> {
            AbonnementMatiere savedAbonnementMatiere = abonnementMatiereRepository.save(new AbonnementMatiere(abonnement, matiere));
            abonnement.ajouterMatiere(savedAbonnementMatiere);
        });

        return abonnement;
    }


    @Transactional
    public Abonnement confirmerPaiement(Long abonnementId, BigDecimal montant, MoyenPaiement moyenPaiement, boolean paiementReussi) {
        Abonnement abonnement = findAbonnementById(abonnementId);

        verifierAbonnementEstEnAttentePaiement(abonnement);

        StatutPaiement status = paiementReussi ? StatutPaiement.REUSSI : StatutPaiement.ECHOUE;
        Paiement savedPaiement = savePaiement(abonnement, montant, moyenPaiement, status);

        if (savedPaiement.getStatus().equals(StatutPaiement.REUSSI)) {
            LocalDate dateDebut = LocalDate.now();
            LocalDate dateFin = dateDebut.plusMonths(12);
            abonnement.activer(dateDebut, dateFin);
        }

        return abonnement;
    }


    public Eleve findEleveById(Long id) {
        return eleveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Élève introuvable"));
    }


    private Formule findFormuleById(Long id) {
        return formuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Formule introuvable"));
    }


    private void verifierAbscenceAbonnementActif(Eleve eleve) {
        boolean abonnementActifExiste = abonnementRepository
                .findByEleveAndStatus(eleve, StatutAbonnement.ACTIF)
                .isPresent();
        if (abonnementActifExiste) {
            throw new BusinessRuleException("Il y a déjà un abonnement actif");
        }
    }


    private List<Matiere> getEtValiderMatieres(List<Long> matiereIds, Eleve eleve) {
        if (matiereIds.isEmpty()) {
            throw new IllegalArgumentException("Vous devez sélectionner au moins une matière");
        }

        if (matiereIds.size() != new java.util.HashSet<>(matiereIds).size()) {
            throw new IllegalArgumentException("Une matière ne peut être sélectionnée qu'une seule fois");
        }

        return matiereIds.stream()
                .map(matiereId -> getEtValiderMatiere(matiereId, eleve))
                .collect(Collectors.toList());
    }


    private Matiere getEtValiderMatiere(Long matiereId, Eleve eleve) {
        Matiere matiere = matiereRepository.findById(matiereId)
                .orElseThrow(() -> new ResourceNotFoundException("Matière introuvable"));

        boolean appartientALaClasseDeEleve = matiere.getClasse()
                .getId().equals(eleve.getClasse().getId());

        if (!appartientALaClasseDeEleve) {
            throw new IllegalArgumentException("La matière " + matiere.getNom() + " n'appartient pas à la classe de l'élève");
        }
        return matiere;
    }


    private void verifierNombreMaxDeMatieres(Formule formule, int nombreDeMatieres) {
        Integer maximum = formule.getNombreMatieresMax();

        if (maximum != null && maximum < nombreDeMatieres) {
            throw new IllegalArgumentException("Le nombre de matieres ne peut pas depassé " + maximum + " !");
        }
    }


    private void verifierAbonnementEstEnAttentePaiement(Abonnement abonnement) {
        boolean abonnementEstEnAttente = abonnement.getStatus().equals(StatutAbonnement.EN_ATTENTE_PAIEMENT);
        if (!abonnementEstEnAttente) {
            throw new BusinessRuleException("Abonnement déjà payé");
        }
    }

    private Paiement savePaiement(Abonnement abonnement, BigDecimal montant, MoyenPaiement moyenPaiement, StatutPaiement statutPaiement) {
        LocalDate datePaiement = LocalDate.now();
        Paiement paiement = new Paiement(abonnement, montant, datePaiement, moyenPaiement, statutPaiement);
        return paiementRepository.save(paiement);
    }

}
