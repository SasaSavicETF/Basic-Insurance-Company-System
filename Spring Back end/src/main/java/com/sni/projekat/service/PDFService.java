package com.sni.projekat.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.sni.projekat.model.Kupovina;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PDFService {
    public String generisiPdf(Kupovina kupovina) throws IOException, DocumentException {
        String fileName = "polisa-" + kupovina.getIdKupovina() + ".pdf";

        String folder = "./pdf";
        new File(folder).mkdirs();

        String path = new File(folder, fileName).getAbsolutePath();

        System.out.println("PDF path: " + path); // debug

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();
        document.add(new Paragraph("Potvrda o kupovini polise"));
        document.add(new Paragraph("Korisnik: " + kupovina.getKorisnik().getIme() + " " + kupovina.getKorisnik().getPrezime()));
        document.add(new Paragraph("Tip: " + kupovina.getPolisa().getTip()));
        document.add(new Paragraph("Opis: " + kupovina.getPolisa().getOpis()));
        document.add(new Paragraph("Cijena: " + kupovina.getIznos() + " KM"));
        document.close();

        File provjera = new File(path);
        System.out.println("Fajl generisan? " + provjera.exists()); // još jedan debug

        return path;
    }
}
