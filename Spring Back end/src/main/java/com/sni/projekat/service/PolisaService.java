package com.sni.projekat.service;

import com.sni.projekat.model.Polisa;
import com.sni.projekat.repo.PolisaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolisaService {
    private final PolisaRepository polisaRepository;

    public PolisaService(PolisaRepository polisaRepository) {
        this.polisaRepository = polisaRepository;
    }

    public List<Polisa> findAllPolise() {
        return this.polisaRepository.findAll();
    }

    public List<Polisa> findAllNekupljenePolise(String name) {
        return this.polisaRepository.findNekupljenePoliseByKorisnik(name);
    }

    public Polisa findPolisaById(Long id) {
        return this.polisaRepository.findById(id).orElse(null);
    }

    public void save(Polisa polisa) {
        this.polisaRepository.save(polisa);
    }

    public void deactivatePolisaById(Long id) {
        this.polisaRepository.deactivatePolisaById(id);
    }

    public boolean existsById(Long id) {
        return this.polisaRepository.existsById(id);
    }
}
