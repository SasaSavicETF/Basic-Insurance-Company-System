package com.sni.projekat.service;

import com.sni.projekat.dto.LoginDTO;
import com.sni.projekat.dto.RegisterDTO;
import com.sni.projekat.dto.VerifikacioniKodDTO;
import com.sni.projekat.model.Korisnik;
import com.sni.projekat.model.Uloga;
import com.sni.projekat.model.VerifikacioniKod;
import com.sni.projekat.repo.KorisnikRepository;
import com.sni.projekat.repo.VerifikacioniKodRepository;
import com.sni.projekat.util.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final KorisnikRepository korisnikRepository;
    private final VerifikacioniKodRepository kodRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public void register(RegisterDTO dto) {
        if(korisnikRepository.findByKorisnickoIme(dto.korisnickoIme()).isPresent()) {
            throw new IllegalArgumentException("Korisničko ime već postoji.");
        }
        if(korisnikRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("Nalog sa unesenim emailom već postoji.");
        }

        Korisnik korisnik = new Korisnik();
        korisnik.setIme(dto.ime());
        korisnik.setPrezime(dto.prezime());
        korisnik.setKorisnickoIme(dto.korisnickoIme());
        korisnik.setLozinka(passwordEncoder.encode(dto.lozinka()));
        korisnik.setEmail(dto.email());
        korisnik.setUloga(Uloga.KLIJENT);

        this.korisnikRepository.save(korisnik);
    }

    public String login(LoginDTO dto) {
        try {
            // Pokreće automatsku provjeru: UserDetailsService + PasswordEncoder
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.korisnickoIme(), dto.lozinka())
            );

            // Ako lozinka nije tačna, baca se AuthenticationException → ide u catch

            // Uspješna autentifikacija – korisnik postoji
            Korisnik korisnik = korisnikRepository.findByKorisnickoIme(dto.korisnickoIme())
                    .orElseThrow(() -> new RuntimeException("Nepostojeći korisnik."));

            // Generisanje verifikacionog koda
            String kod = String.format("%06d", (int) (Math.random() * 1_000_000));
            LocalDateTime time = LocalDateTime.now().plusMinutes(5);
            VerifikacioniKod entitet = new VerifikacioniKod(null, kod,
                    time, korisnik);

            kodRepository.save(entitet);
            // Slanje mail-a
            this.mailService.posaljiKod(korisnik.getEmail(), kod);
            log.info("2FA kod za korisnika {}: {}", korisnik.getKorisnickoIme(), kod);

            return time.toString();

        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("Pogrešno korisničko ime ili lozinka.");
        }
    }

    public String verifyCode(VerifikacioniKodDTO dto, HttpServletResponse response) {
        var korisnik = korisnikRepository.findByKorisnickoIme(dto.korisnickoIme())
                .orElseThrow(() -> new RuntimeException("Nepostojeći korisnik."));

        var kod = kodRepository.findTopByKorisnikOrderByTrajanjeDesc(korisnik)
                .orElseThrow(() -> new RuntimeException("Kod nije pronađen"));

        if(!kod.getKod().equals(dto.kod()) || kod.getTrajanje().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Kod je neispravan ili istekao.");
        }

        String jwt = this.jwtUtil.generateToken(korisnik.getKorisnickoIme());

        ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(3600)
                .sameSite("Lax")
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return "Uspješna prijava.";
    }

    public String logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .sameSite("strict")
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return "Odjavljen";
    }
}
