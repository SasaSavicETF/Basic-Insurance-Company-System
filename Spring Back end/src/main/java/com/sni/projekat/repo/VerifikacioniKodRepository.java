package com.sni.projekat.repo;

import com.sni.projekat.model.Korisnik;
import com.sni.projekat.model.VerifikacioniKod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerifikacioniKodRepository extends JpaRepository<VerifikacioniKod, Long> {
    Optional<VerifikacioniKod> findTopByKorisnikOrderByTrajanjeDesc(Korisnik korisnik);
}
