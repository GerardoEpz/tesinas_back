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

    @PreAuthorize("isAuthenticated()") //pre authorize the request if the user is autheticated (JWT is provided and is valid)
    @GetMapping("/getall")
    public ResponseEntity<?> requestAllQuestions(){
        try {
            List<Faq> faqs = faqRepository.findAll();
            return ResponseEntity.ok(faqs);
        }catch (Exception error){
            return ResponseEntity.badRequest().body(error);
        }

    }

    @PostMapping("/addFaq")
    @PreAuthorize(" hasRole('PROFESOR') or hasRole('ASESOR') or hasRole('DIRECTOR')") //only those roles have access to this method
    public ResponseEntity<?> addFaq(@RequestBody Faq faq){
        try {
            faqRepository.save(faq);
            return ResponseEntity.ok().body("FAQ Updated");
        } catch (Exception error){
            return ResponseEntity.badRequest().body("Could not update FAQ" + error);
        }
    }

    @DeleteMapping("/delete-faq")
    @PreAuthorize(" hasRole('PROFESOR') or hasRole('ASESOR') or hasRole('DIRECTOR')") //only those roles have access to this method
    public ResponseEntity<?> deleteFaq(@RequestBody Faq faq){
        try {
            faqRepository.delete(faq);
            return ResponseEntity.ok().body("FAQ Deleted");
        } catch (Exception error){
            return ResponseEntity.badRequest().body("Could not delete FAQ" + error);
        }
    }
}
