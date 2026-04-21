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
public class BezbjednosniDogadjaj {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDogadjaj;

    @Column(nullable = false)
    private LocalDateTime vrijeme;

    @Column(nullable = false)
    private String akcija;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String detalji;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_korisnik")
    private Korisnik korisnik;
}
