package com.sni.projekat.repo;

import com.sni.projekat.model.BezbjednosniDogadjaj;
import com.sni.projekat.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BezbjednosniDogagjajRepository extends JpaRepository<BezbjednosniDogadjaj, Long> {
    List<BezbjednosniDogadjaj> findByKorisnik(Korisnik korisnik);
}
