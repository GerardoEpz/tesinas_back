package com.tesinas.spring.jwt.mongodb.controllers;

import com.tesinas.spring.jwt.mongodb.models.Faq;
import com.tesinas.spring.jwt.mongodb.repository.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/faq")
public class FaqController {

    @Autowired
    FaqRepository faqRepository;

    @GetMapping("/getall")
    public ResponseEntity<?> requestAllQuestions(){
        List<Faq> faqs = faqRepository.findAll();
        return ResponseEntity.ok(faqs);
    }

    @PostMapping("/addFaq")
//    @PreAuthorize(" hasRole('PROFESOR') or hasRole('ASESOR') or hasRole('DIRECTOR')")
    public ResponseEntity<?> addFaq(@RequestBody Faq faq){
        try {
            faqRepository.save(faq);
            return ResponseEntity.ok().body("FAQ Updated");
        } catch (Exception error){
            return ResponseEntity.badRequest().body("Could not update FAQ");
        }
    }
}
