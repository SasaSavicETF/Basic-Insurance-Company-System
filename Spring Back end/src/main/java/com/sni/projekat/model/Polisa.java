package com.sni.projekat.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "polisa_osiguranja")
public class Polisa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPolisa;

    @Column(nullable = false)
    private String tip;

    @Column(nullable = false)
    private String opis;

    @Column(nullable = false)
    private Double cijena;

    @Column(nullable = false)
    private boolean aktivna;
}
