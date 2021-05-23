package com.tesinas.spring.jwt.mongodb.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/filess")
public class EoController {
    @PostMapping("/eo")
    public ResponseEntity<?> eo(){
        return ResponseEntity.ok("aaaa");
    }
}
