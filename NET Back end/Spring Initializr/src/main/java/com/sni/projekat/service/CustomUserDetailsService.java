package com.sni.projekat.service;

import com.sni.projekat.model.Korisnik;
import com.sni.projekat.repo.KorisnikRepository;
import com.sni.projekat.security.KorisnikDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final KorisnikRepository korisnikRepository;

    public CustomUserDetailsService(KorisnikRepository korisnikRepository) {
        this.korisnikRepository = korisnikRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Korisnik korisnik = korisnikRepository.findByKorisnickoIme(username)
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik nije pronađen."));
        return new KorisnikDetails(korisnik);
    }
}
