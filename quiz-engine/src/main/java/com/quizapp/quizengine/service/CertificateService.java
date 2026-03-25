package com.quizapp.quizengine.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.quizapp.quizengine.model.*;
import com.quizapp.quizengine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CertificateService {

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizRepository quizRepository;

    public byte[] generateCertificate(Long attemptId) throws Exception {

        Attempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found!"));

        if (!attempt.isPassed()) {
            throw new RuntimeException("Cannot generate certificate - Quiz not passed!");
        }

        User user = attempt.getUser();
        Quiz quiz = attempt.getQuiz();

        Certificate certificate = certificateRepository
                .findByUserIdAndQuizId(user.getId(), quiz.getId())
                .orElseGet(() -> {
                    Certificate newCert = new Certificate();
                    newCert.setUser(user);
                    newCert.setQuiz(quiz);
                    newCert.setCertificateNumber("CERT-" + System.currentTimeMillis());
                    newCert.setIssuedDate(LocalDateTime.now());
                    return certificateRepository.save(newCert);
                });

        return createPDF(user, quiz, attempt, certificate);
    }

    private byte[] createPDF(User user, Quiz quiz,
                              Attempt attempt, Certificate certificate)
                              throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, out);
        document.open();

        // Fonts
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 36,
            Font.BOLD, new BaseColor(0, 102, 204));
        Font headingFont = new Font(Font.FontFamily.HELVETICA, 24,
            Font.BOLD, BaseColor.BLACK);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 16,
            Font.NORMAL, BaseColor.BLACK);
        Font nameFont = new Font(Font.FontFamily.HELVETICA, 28,
            Font.BOLD | Font.ITALIC, new BaseColor(0, 102, 204));
        Font smallFont = new Font(Font.FontFamily.HELVETICA, 12,
            Font.NORMAL, BaseColor.GRAY);

        // Top spacing
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        // Title
        Paragraph title = new Paragraph("CERTIFICATE OF COMPLETION", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(10);
        document.add(title);

        // Divider using dashes
        Paragraph divider = new Paragraph(
            "─────────────────────────────────────────────────────",
            new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL,
                new BaseColor(0, 102, 204)));
        divider.setAlignment(Element.ALIGN_CENTER);
        document.add(divider);

        // Subtitle
        Paragraph subtitle = new Paragraph("This is to certify that", normalFont);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        subtitle.setSpacingBefore(20);
        subtitle.setSpacingAfter(10);
        document.add(subtitle);

        // Student Name
        Paragraph name = new Paragraph(user.getName(), nameFont);
        name.setAlignment(Element.ALIGN_CENTER);
        name.setSpacingAfter(15);
        document.add(name);

        // Body text
        Paragraph body = new Paragraph(
            "has successfully completed the quiz", normalFont);
        body.setAlignment(Element.ALIGN_CENTER);
        body.setSpacingAfter(10);
        document.add(body);

        // Quiz Title
        Paragraph quizTitle = new Paragraph(
            "\"" + quiz.getTitle() + "\"", headingFont);
        quizTitle.setAlignment(Element.ALIGN_CENTER);
        quizTitle.setSpacingAfter(15);
        document.add(quizTitle);

        // Score
        Paragraph score = new Paragraph(
            "with a score of " + attempt.getScore() + "%", normalFont);
        score.setAlignment(Element.ALIGN_CENTER);
        score.setSpacingAfter(30);
        document.add(score);

        // Divider
        Paragraph divider2 = new Paragraph(
            "─────────────────────────────────────────────────────",
            new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL,
                new BaseColor(0, 102, 204)));
        divider2.setAlignment(Element.ALIGN_CENTER);
        document.add(divider2);

        // Certificate details
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String dateStr = certificate.getIssuedDate().format(formatter);

        Paragraph details = new Paragraph(
            "Certificate No: " + certificate.getCertificateNumber()
            + "     |     Date: " + dateStr, smallFont);
        details.setAlignment(Element.ALIGN_CENTER);
        details.setSpacingBefore(15);
        document.add(details);

        document.close();
        return out.toByteArray();
    }
}