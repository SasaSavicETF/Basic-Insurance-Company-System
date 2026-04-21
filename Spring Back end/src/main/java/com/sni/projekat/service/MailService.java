package com.sni.projekat.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
@Transactional
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    public void posaljiPdf(String kome, String pdfPutanja) throws MessagingException {
        System.out.println("Putanja: " + pdfPutanja);
        MimeMessage poruka = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(poruka, true);
        helper.setTo(kome);
        helper.setSubject("Vaša kupljena polisa");
        helper.setText("Poštovani, u prilogu se nalazi vaša kupljena polisa.");

        FileSystemResource file = new FileSystemResource(new File(pdfPutanja));
        helper.addAttachment("polisa.pdf", file);

        this.mailSender.send(poruka);
    }

    @Async
    public void posaljiKod(String email, String kod) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("eOsiguranje");
        mailMessage.setText("Vaš pristupni kod za autentikaciju: " + kod + ". " +
                "Trajanje koda je 5min nakon čega se kod poništava.");
        this.mailSender.send(mailMessage);
    }
}
