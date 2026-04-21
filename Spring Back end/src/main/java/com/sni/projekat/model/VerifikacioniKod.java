package com.sni.projekat.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifikacioniKod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String kod;

    @Column(nullable = false)
    private LocalDateTime trajanje;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_korisnik")
    private Korisnik korisnik;
}
