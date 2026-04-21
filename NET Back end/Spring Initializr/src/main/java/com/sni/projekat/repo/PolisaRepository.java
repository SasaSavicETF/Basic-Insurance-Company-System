package com.sni.projekat.repo;

import com.sni.projekat.model.Polisa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PolisaRepository extends JpaRepository<Polisa, Long> {
    @Query("SELECT p FROM Polisa p WHERE p.idPolisa NOT IN " +
            "(SELECT k.polisa.idPolisa FROM Kupovina k WHERE k.korisnik.korisnickoIme = :name)")
    List<Polisa> findNekupljenePoliseByKorisnik(@Param("name") String name);

    @Modifying
    @Transactional
    @Query("UPDATE Polisa p SET p.aktivna = false WHERE p.idPolisa = :id")
    void deactivatePolisaById(@Param("id") Long id);
}