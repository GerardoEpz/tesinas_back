package com.tesinas.spring.jwt.mongodb.controllers;

import com.tesinas.spring.jwt.mongodb.models.Faq;
import com.tesinas.spring.jwt.mongodb.repository.FaqRepository;
import com.tesinas.spring.jwt.mongodb.security.services.UserDetailsImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
