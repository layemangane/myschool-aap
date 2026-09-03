package org.myschool.service;

import lombok.AllArgsConstructor;
import org.myschool.dto.ClasseReqDTO;
import org.myschool.entity.Classe;
import org.myschool.exception.ResourceNotFoundException;
import org.myschool.repository.ClasseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ClasseService {

    private final ClasseRepository classeRepository;

    public Classe createClasse(ClasseReqDTO dto){
        Classe classe = new Classe(dto.getNom(), dto.getOrdre());
        return classeRepository.save(classe);
    }

    public List<Classe> findAllClasses(){
        return classeRepository.findAll();
    }

    public Classe findClasseById(Long id){
        return classeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classe introuvable"));
    }

    @Transactional
    public Classe updateClasse(Long id, ClasseReqDTO dto) {
        Classe classe = findClasseById(id);
        classe.setNom(dto.getNom());
        classe.setOrdre(dto.getOrdre());
        return classeRepository.save(classe);
    }

    @Transactional
    public void deleteClasse(Long id) {
        Classe classe = findClasseById(id);
        classeRepository.delete(classe);
    }

}
