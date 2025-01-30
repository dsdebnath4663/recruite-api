package com.recruitment.controller;

import com.recruitment.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/offer-letter")
public class OfferLetterController {

    private final PdfService pdfService;

    public OfferLetterController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateOfferLetter(@RequestBody Map<String, String> offerDetails) throws IOException {
        byte[] pdfBytes = pdfService.generateOfferLetter(offerDetails);

        return ResponseEntity.ok()
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Offer_Letter.pdf")
                             .contentType(MediaType.APPLICATION_PDF)
                             .body(pdfBytes);
    }
}
