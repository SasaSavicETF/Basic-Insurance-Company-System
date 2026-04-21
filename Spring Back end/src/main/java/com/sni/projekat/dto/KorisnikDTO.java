package com.sni.projekat.dto;

import com.sni.projekat.model.Uloga;

public record KorisnikDTO(
        Long idKorisnik,
        String ime,
        String prezime,
        String korisnickoIme,
        String email,
        Uloga uloga
) {}
