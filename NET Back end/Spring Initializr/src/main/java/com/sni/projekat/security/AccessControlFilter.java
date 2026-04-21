package com.sni.projekat.security;

import com.sni.projekat.model.Korisnik;
import com.sni.projekat.repo.KorisnikRepository;
import com.sni.projekat.service.SiemLoggerService;
import com.sni.projekat.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccessControlFilter extends OncePerRequestFilter {
    private final SiemLoggerService siemLoggerService;
    private final KorisnikRepository korisnikRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        String method = request.getMethod();

        // Blokiranje zahtjeva za kupovinu sa prevelikim iznosom
        if (uri.contains("/klijent/kupi") && method.equalsIgnoreCase("POST")) {
            String body = new String(request.getInputStream().readAllBytes());
            if (body.contains("\"iznos\":") && body.contains("10000")) {
                blokirajIP(request, response, "Sumnjiv iznos u kupovini.");
                return;
            }
        }

        // Blokiranje zahtjeva sa sumnjivim parametrom 'debug'
        if ("true".equalsIgnoreCase(request.getParameter("debug"))) {
            blokirajIP(request, response, "Pokušaj korištenja sumnjivog 'debug' parametra.");
            return;
        }


        // Blokiranje za veliki broj parametara (RequestParam)
        if (request.getParameterMap().size() > 5) {
            blokirajIP(request, response, "Prevelik broj query parametara - potencijalni napad.");
            return;
        }


        // Blokiranje sumnjivog User-Agenta
        String agent = request.getHeader("User-Agent");
        if (agent != null && (agent.toLowerCase().contains("sqlmap") || agent.contains("curl"))) {
            blokirajIP(request, response, "Sumnjiv User-Agent: " + agent);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void blokirajIP(HttpServletRequest request, HttpServletResponse response, String razlog) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            Optional<Korisnik> korisnik = this.korisnikRepository.findByKorisnickoIme(username);
            korisnik.ifPresent(k -> siemLoggerService.log(k, "BLOCKED_REQUEST", razlog));

            ResponseCookie cookie = ResponseCookie.from("jwt", "")
                    .httpOnly(true).path("/").maxAge(0).build();
            response.setHeader("Set-Cookie", cookie.toString());

            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Zahtev blokiran: " + razlog);
        }
    }
}



