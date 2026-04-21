package com.sni.projekat.controller;

import com.sni.projekat.dto.LoginDTO;
import com.sni.projekat.dto.RegisterDTO;
import com.sni.projekat.dto.VerifikacioniKodDTO;
import com.sni.projekat.model.Korisnik;
import com.sni.projekat.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO dto) {
        try {
            this.authService.register(dto);
            return ResponseEntity.status(200).body("Registracija uspješna!");
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        try {
            String poruka = this.authService.login(dto);
            return ResponseEntity.ok(poruka);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(403).body(e.getMessage()); // FORBIDDEN
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Greška na serveru.");
        }
    }

    @PostMapping("/verifikacija-koda")
    public ResponseEntity<String> verifyCode(@RequestBody VerifikacioniKodDTO dto, HttpServletResponse response) {
        try {
            String poruka = this.authService.verifyCode(dto, response);
            return ResponseEntity.ok(poruka);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Greška na serveru.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        String poruka = this.authService.logout(response);
        return ResponseEntity.ok(poruka);
    }

    @GetMapping("/status")
    public ResponseEntity<Boolean> checkAuthStatus(Authentication authentication) {
        return ResponseEntity.ok(authentication != null && authentication.isAuthenticated());
    }
}
