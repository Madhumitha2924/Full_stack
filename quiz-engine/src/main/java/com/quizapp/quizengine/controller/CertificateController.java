package com.quizapp.quizengine.controller;

import com.quizapp.quizengine.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/certificate")
@CrossOrigin(origins = "*")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @GetMapping("/generate/{attemptId}")
    public ResponseEntity<byte[]> generateCertificate(
            @PathVariable Long attemptId) {
        try {
            byte[] pdf = certificateService.generateCertificate(attemptId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                ContentDisposition.attachment()
                    .filename("certificate.pdf")
                    .build()
            );

            return ResponseEntity.ok().headers(headers).body(pdf);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}