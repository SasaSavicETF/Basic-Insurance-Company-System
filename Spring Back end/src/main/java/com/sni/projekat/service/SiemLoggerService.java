package com.sni.projekat.service;

import com.sni.projekat.model.BezbjednosniDogadjaj;
import com.sni.projekat.model.Korisnik;
import com.sni.projekat.repo.BezbjednosniDogagjajRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SiemLoggerService {
    private final BezbjednosniDogagjajRepository bdRepository;

    public void log(Korisnik k, String akcija, String detalji) {
        BezbjednosniDogadjaj dogadjaj = new BezbjednosniDogadjaj();
        dogadjaj.setKorisnik(k);
        dogadjaj.setVrijeme(LocalDateTime.now());
        dogadjaj.setAkcija(akcija);
        dogadjaj.setDetalji(detalji);
        this.bdRepository.save(dogadjaj);
    }

    public List<BezbjednosniDogadjaj> findAllDogadjajs() {
        return this.bdRepository.findAll();
    }
}
