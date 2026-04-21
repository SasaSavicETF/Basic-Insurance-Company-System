package com.sni.projekat.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Kupovina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKupovina;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_korisnik")
    private Korisnik korisnik;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_polisa")
    private Polisa polisa;

    @Column(nullable = false)
    private LocalDateTime datumUplate;

    @Column(nullable = false)
    private double iznos;

    @Column(nullable = false)
    private String status;
}

