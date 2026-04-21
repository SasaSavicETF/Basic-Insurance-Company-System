package com.sni.projekat.controller;

import com.sni.projekat.model.Korisnik;
import com.sni.projekat.model.Kupovina;
import com.sni.projekat.model.Polisa;
import com.sni.projekat.repo.KorisnikRepository;
import com.sni.projekat.service.*;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/klijent")
@PreAuthorize("hasAnyRole('KLIJENT', 'ADMIN')")
@RequiredArgsConstructor
public class KlijentController {
    private final PolisaService polisaService;
    private final KupovinaService kupovinaService;
    private final KorisnikRepository korisnikRepository;
    private final PDFService pdfService;
    private final MailService mailService;
    private final StripeService stripeService;

    @GetMapping("/moje-kupovine")
    public ResponseEntity<List<Kupovina>> getMojePolise(Authentication authentication) {
        String korisnickoIme = authentication.getName();
        Korisnik korisnik = this.korisnikRepository.findByKorisnickoIme(korisnickoIme)
                .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen."));
        List<Kupovina> mojeKupovine = this.kupovinaService.getMojeKupovine(korisnik);
        return new ResponseEntity<>(mojeKupovine, HttpStatus.OK);
    }

    @GetMapping("/polise")
    public ResponseEntity<List<Polisa>> getNekupljenePolise(Authentication authentication) {
        String name = authentication.getName();
        List<Polisa> polise = this.polisaService.findAllNekupljenePolise(name);
        return new ResponseEntity<>(polise, HttpStatus.OK);
    }

    @PostMapping("/stripe/checkout-session/{idPolise}")
    public ResponseEntity<?> kreirajCheckoutSesiju(@PathVariable Long idPolise) {
        try {
            Polisa polisa = polisaService.findPolisaById(idPolise);

            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:4201/polise?success=true&session_id={CHECKOUT_SESSION_ID}&idPolise=" + idPolise)
                    .setCancelUrl("http://localhost:4201/polise?canceled=true")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("eur")
                                                    .setUnitAmount(polisa.getCijena().longValue() * 100)
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(polisa.getTip())
                                                                    .build())
                                                    .build())
                                    .build())
                    .build();

            Session session = Session.create(params);
            return ResponseEntity.ok(Map.of("sessionId", session.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Greška pri kreiranju sesije.");
        }
    }


    @PostMapping("/kupi/{idPolise}")
    public ResponseEntity<String> kupiPolisu(@PathVariable Long idPolise,
                                        @RequestParam String sessionId,
                                             Authentication authentication) {
        try {
            String korisnickoIme = authentication.getName();
            Korisnik korisnik = korisnikRepository.findByKorisnickoIme(korisnickoIme)
                    .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));

            // Dohvati Stripe sesiju
            Session session = stripeService.dobaviSesiju(sessionId);
            String paymentIntentId = session.getPaymentIntent();

            // Provjeri da li je plaćanje uspjelo
            if (!stripeService.isPaymentSucceeded(paymentIntentId)) {
                return new ResponseEntity<>("Plaćanje nije uspjelo.", HttpStatus.BAD_REQUEST);
            }

            // Kreiraj kupovinu
            Polisa polisa = polisaService.findPolisaById(idPolise);

            Kupovina kupovina = new Kupovina();
            kupovina.setKorisnik(korisnik);
            kupovina.setPolisa(polisa);
            kupovina.setIznos(polisa.getCijena());
            kupovina.setDatumUplate(LocalDateTime.now());
            kupovina.setStatus("USPJESNA");

            kupovina = this.kupovinaService.save(kupovina);

            try {
                String path = this.pdfService.generisiPdf(kupovina);
                this.mailService.posaljiPdf(korisnik.getEmail(), path);
            } catch (Exception e) {
                return ResponseEntity.ok("Polisa kupljena, ali PDF nije poslan.");
            }

            return ResponseEntity.ok("Polisa uspješno kupljena i poslata na email.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Greška pri obradi kupovine.");
        }
    }
}
