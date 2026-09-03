package org.myschool.service;

import lombok.AllArgsConstructor;
import org.myschool.entity.Eleve;
import org.myschool.repository.EleveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EleveService {

    private final EleveRepository eleveRepository;

    public List<Eleve> findAllEleves() {
        return eleveRepository.findAll();
    }

}
