package com.sni.projekat.service;

import com.sni.projekat.model.Korisnik;
import com.sni.projekat.model.Kupovina;
import com.sni.projekat.model.Polisa;
import com.sni.projekat.repo.KupovinaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class KupovinaService {
    private final KupovinaRepository kupovinaRepository;

    public KupovinaService(KupovinaRepository kupovinaRepository) {
        this.kupovinaRepository = kupovinaRepository;
    }

    public List<Kupovina> findAllKupovine() {
        return this.kupovinaRepository.findAll();
    }

    public List<Kupovina> getMojeKupovine(Korisnik korisnik) {
        return this.kupovinaRepository.findByKorisnik(korisnik);
    }

    public Kupovina save(Kupovina kupovina) {
        return this.kupovinaRepository.save(kupovina);
    }
}
