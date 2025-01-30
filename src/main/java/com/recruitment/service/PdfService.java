package com.recruitment.service;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@Service
public class PdfService {

    private static final float MARGIN = 50;
    private static final float LEADING = 18f; // Line spacing
    private static final float FONT_SIZE = 12;
    private static final float PAGE_HEIGHT = PDRectangle.A4.getHeight() - MARGIN;
    private static final float PAGE_WIDTH = PDRectangle.A4.getWidth() - (2 * MARGIN);

    public byte[] generateOfferLetter(Map<String, String> offerDetails) throws IOException {
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE);
            contentStream.setLeading(LEADING);
            contentStream.beginText();
            float yPosition = PAGE_HEIGHT;  // Track the Y position
            contentStream.newLineAtOffset(MARGIN, yPosition);

            // Write text in a formatted manner, breaking into multiple pages if necessary
            addTextWithWrapping(contentStream, document, offerDetails.get("companyName"), true, yPosition);
            yPosition -= LEADING;
            addTextWithWrapping(contentStream, document, offerDetails.get("companyAddress"), false, yPosition);
            yPosition -= LEADING;
            addTextWithWrapping(contentStream, document, offerDetails.get("companyCity") + ", " + offerDetails.get("companyState") + " " + offerDetails.get("companyZip"), false, yPosition);
            yPosition -= LEADING;
            addEmptyLines(contentStream, yPosition, 1);

            addTextWithWrapping(contentStream, document, "Date: " + LocalDate.now(), false, yPosition);
            yPosition -= LEADING;
            addEmptyLines(contentStream, yPosition, 1);

            // Candidate Info
            addTextWithWrapping(contentStream, document, offerDetails.get("candidateName"), true, yPosition);
            yPosition -= LEADING;
            addTextWithWrapping(contentStream, document, offerDetails.get("candidateAddress"), false, yPosition);
            yPosition -= LEADING;
            addTextWithWrapping(contentStream, document, offerDetails.get("candidateCity") + ", " + offerDetails.get("candidateState") + " " + offerDetails.get("candidateZip"), false, yPosition);
            yPosition -= LEADING;
            addEmptyLines(contentStream, yPosition, 1);

            // Subject
            addTextWithWrapping(contentStream, document, "Subject: Offer of Employment at " + offerDetails.get("companyName"), true, yPosition);
            yPosition -= LEADING;
            addEmptyLines(contentStream, yPosition, 1);

            // Salutation
            addTextWithWrapping(contentStream, document, "Dear " + offerDetails.get("candidateName") + ",", false, yPosition);
            yPosition -= LEADING;
            addEmptyLines(contentStream, yPosition, 1);
            addTextWithWrapping(contentStream, document, "We are excited to offer you the position of " + offerDetails.get("jobTitle") + " at " + offerDetails.get("companyName") + ". We are impressed with your background and believe you will be a valuable addition to our team.", false, yPosition);
            yPosition -= LEADING;
            addEmptyLines(contentStream, yPosition, 1);

            // Position & Compensation
            addSectionTitle(contentStream, document, "Position & Compensation", true, yPosition);
            yPosition -= LEADING;
            addBulletPoint(contentStream, document, "Start Date: " + offerDetails.get("startDate"), yPosition);
            yPosition -= LEADING;
            addBulletPoint(contentStream, document, "Employment Type: " + offerDetails.get("employmentType"), yPosition);
            yPosition -= LEADING;
            addBulletPoint(contentStream, document, "Base Salary: " + offerDetails.get("salary") + " per " + offerDetails.get("salaryType"), yPosition);
            yPosition -= LEADING;
            addBulletPoint(contentStream, document, "Equity: " + offerDetails.get("equity"), yPosition);
            yPosition -= LEADING;
            addBulletPoint(contentStream, document, "Benefits: " + offerDetails.get("benefits"), yPosition);
            yPosition -= LEADING;
            addEmptyLines(contentStream, yPosition, 2);

            // At-Will Employment
            addSectionTitle(contentStream, document, "At-Will Employment", true, yPosition);
            yPosition -= LEADING;
            addTextWithWrapping(contentStream, document, "Your employment with " + offerDetails.get("companyName") + " will be at-will, meaning either you or the company may terminate the employment relationship at any time, with or without cause or notice.", false, yPosition);
            yPosition -= LEADING;
            addEmptyLines(contentStream, yPosition, 2);

            // Confidentiality & IP
            addSectionTitle(contentStream, document, "Confidentiality & Intellectual Property", true, yPosition);
            yPosition -= LEADING;
            addTextWithWrapping(contentStream, document, "As a condition of employment, you will be required to sign our Confidentiality and Intellectual Property Agreement, ensuring the protection of our proprietary information.", false, yPosition);
            yPosition -= LEADING;
            addEmptyLines(contentStream, yPosition, 2);

            // Acceptance
            addSectionTitle(contentStream, document, "Acceptance", true, yPosition);
            yPosition -= LEADING;
            addTextWithWrapping(contentStream, document, "Please sign and return this letter by " + offerDetails.get("deadlineDate") + " to confirm your acceptance of this offer. If you have any questions, feel free to reach out. We look forward to working together!", false, yPosition);
            yPosition -= LEADING;
            addEmptyLines(contentStream, yPosition, 1);

            // Signature Section - Ensure there is enough space
            if (yPosition - LEADING < MARGIN) {
                // If not enough space, start a new page
                contentStream.endText();
                contentStream.close();
                PDPage newPage = new PDPage(PDRectangle.A4);
                document.addPage(newPage);
                contentStream = new PDPageContentStream(document, newPage);
                contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE);
                contentStream.setLeading(LEADING);
                contentStream.beginText();
                contentStream.newLineAtOffset(MARGIN, PAGE_HEIGHT);
                yPosition = PAGE_HEIGHT;
            }
            addTextWithWrapping(contentStream, document, "Sincerely,", false, yPosition);
            yPosition -= LEADING;
            addEmptyLines(contentStream, yPosition, 1);
            addTextWithWrapping(contentStream, document, offerDetails.get("hiringManagerName"), false, yPosition);
            yPosition -= LEADING;
            addTextWithWrapping(contentStream, document, offerDetails.get("hiringManagerPosition"), false, yPosition);
            yPosition -= LEADING;
            addTextWithWrapping(contentStream, document, offerDetails.get("companyName"), false, yPosition);
            yPosition -= LEADING;
            addEmptyLines(contentStream, yPosition, 2);
            addTextWithWrapping(contentStream, document, "Accepted by: " + offerDetails.get("candidateName"), false, yPosition);
            yPosition -= LEADING;
            addTextWithWrapping(contentStream, document, "Signature: ______________________", false, yPosition);
            yPosition -= LEADING;
            addTextWithWrapping(contentStream, document, "Date: ______________________", false, yPosition);

            contentStream.endText();
            contentStream.close();

            document.save(outputStream);
            return outputStream.toByteArray();
        }
    }

    private void addTextWithWrapping(PDPageContentStream contentStream, PDDocument document, String text, boolean bold, float yPosition) throws IOException {
        PDType1Font font = bold ? PDType1Font.HELVETICA_BOLD : PDType1Font.HELVETICA;
        contentStream.setFont(font, FONT_SIZE);

        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if ((font.getStringWidth(line.toString() + word) / 1000 * FONT_SIZE) > PAGE_WIDTH) {
                contentStream.showText(line.toString().trim());
                contentStream.newLine();
                yPosition -= LEADING;
                if (yPosition < MARGIN) {
                    contentStream.endText();
                    contentStream.close();
                    PDPage newPage = new PDPage(PDRectangle.A4);
                    document.addPage(newPage);
                    contentStream = new PDPageContentStream(document, newPage);
                    contentStream.setFont(font, FONT_SIZE);
                    contentStream.setLeading(LEADING);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(MARGIN, PAGE_HEIGHT);
                    yPosition = PAGE_HEIGHT;
                }
                line = new StringBuilder();
            }
            line.append(word).append(" ");
        }
        contentStream.showText(line.toString().trim());
        contentStream.newLine();
        yPosition -= LEADING;
    }

    private void addSectionTitle(PDPageContentStream contentStream, PDDocument document, String title, boolean bold, float yPosition) throws IOException {
        addTextWithWrapping(contentStream, document, title, bold, yPosition);
    }

    private void addBulletPoint(PDPageContentStream contentStream, PDDocument document, String text, float yPosition) throws IOException {
        addTextWithWrapping(contentStream, document, "- " + text, false, yPosition);
    }

    private void addEmptyLines(PDPageContentStream contentStream, float yPosition, int count) throws IOException {
        for (int i = 0; i < count; i++) {
            contentStream.newLine();
            yPosition -= LEADING;
        }
    }
}
