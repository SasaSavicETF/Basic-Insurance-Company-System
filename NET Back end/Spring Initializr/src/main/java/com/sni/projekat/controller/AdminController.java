package com.sni.projekat.controller;

import com.sni.projekat.dto.KorisnikDTO;
import com.sni.projekat.dto.RegisterDTO;
import com.sni.projekat.model.*;
import com.sni.projekat.repo.KorisnikRepository;
import com.sni.projekat.service.KupovinaService;
import com.sni.projekat.service.PolisaService;
import com.sni.projekat.service.SiemLoggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final KorisnikRepository korisnikRepository;
    private final PolisaService polisaService;
    private final KupovinaService kupovinaService;
    private final SiemLoggerService bdService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/klijenti")
    public ResponseEntity<List<KorisnikDTO>> getAllKlijents() {
        List<KorisnikDTO> korisnici = this.korisnikRepository.findAll()
                .stream().map(korisnik -> new KorisnikDTO(
                        korisnik.getIdKorisnik(),
                        korisnik.getIme(),
                        korisnik.getPrezime(),
                        korisnik.getKorisnickoIme(),
                        korisnik.getEmail(),
                        korisnik.getUloga()
                )).toList();
        return new ResponseEntity<>(korisnici, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addAdmin(@RequestBody RegisterDTO dto) {
        Korisnik korisnik = new Korisnik();
        korisnik.setIme(dto.ime());
        korisnik.setPrezime(dto.prezime());
        korisnik.setKorisnickoIme(dto.korisnickoIme());
        korisnik.setLozinka(passwordEncoder.encode(dto.lozinka()));
        korisnik.setEmail(dto.email());
        korisnik.setUloga(Uloga.ADMIN);

        this.korisnikRepository.save(korisnik);
        return new ResponseEntity<>("Novi admin registrovan.", HttpStatus.OK);
    }

    @PutMapping("/klijenti/{id}")
    public ResponseEntity<?> updateKlijent(@PathVariable Long id, @RequestBody KorisnikDTO dto) {
        Optional<Korisnik> opt = this.korisnikRepository.findById(id);
        if(opt.isPresent()) {
            Korisnik korisnik = opt.get();
            korisnik.setIme(dto.ime());
            korisnik.setPrezime(dto.prezime());
            korisnik.setKorisnickoIme(dto.korisnickoIme());
            korisnik.setEmail(dto.email());
            korisnik.setUloga(dto.uloga());

            this.korisnikRepository.save(korisnik);
            return new ResponseEntity<>("Korisnik ažuriran.", HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/klijenti/{id}")
    public ResponseEntity<?> deleteKlijent(@PathVariable Long id) {
        if (!this.korisnikRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        this.korisnikRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/polise")
    public ResponseEntity<List<Polisa>> getPolise() {
        List<Polisa> polise = this.polisaService.findAllPolise();
        return new ResponseEntity<>(polise, HttpStatus.OK);
    }

    @PostMapping("/polise")
    public ResponseEntity<String> addPolisa(@RequestBody Polisa polisa) {
        polisa.setAktivna(true);
        this.polisaService.save(polisa);
        return new ResponseEntity<>("Polisa dodata.", HttpStatus.OK);
    }

    @PutMapping("/polise/{id}")
    public ResponseEntity<String> updatePolisaById(@PathVariable Long id, @RequestBody Polisa novaPolisa) {
        Polisa polisa = this.polisaService.findPolisaById(id);
        if(polisa != null) {
            polisa.setTip(novaPolisa.getTip());
            polisa.setOpis(novaPolisa.getOpis());
            polisa.setCijena(novaPolisa.getCijena());
            polisa.setAktivna(novaPolisa.isAktivna());

            this.polisaService.save(polisa);
            return new ResponseEntity<>("Polisa ažurirana.", HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/polise/{id}")
    public ResponseEntity<String> deactivatePolisa(@PathVariable Long id) {
        System.out.println(id);
        if(!this.polisaService.existsById(id)) {
            return new ResponseEntity<>("Greška pri deaktiviranju polise.", HttpStatus.NOT_FOUND);
        } else {
            this.polisaService.deactivatePolisaById(id);
            return new ResponseEntity<>("Polisa uspješno deaktivirana.", HttpStatus.OK);
        }
    }

    @GetMapping("/kupovine")
    public ResponseEntity<List<Kupovina>> getAllKupovinas() {
        List<Kupovina> kupovine = this.kupovinaService.findAllKupovine();
        return new ResponseEntity<>(kupovine, HttpStatus.OK);
    }

    @GetMapping("/logovi")
    public ResponseEntity<List<BezbjednosniDogadjaj>> getAllDogadjajs() {
        List<BezbjednosniDogadjaj> dogadjaji = this.bdService.findAllDogadjajs();
        return new ResponseEntity<>(dogadjaji, HttpStatus.OK);
    }
}
