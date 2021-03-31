package com.tesinas.spring.jwt.mongodb.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tesinas")
public class Tesinas {

    @Id
    private String id;

    private String titulo;

    public Tesinas(){

    }
    public Tesinas(String titulo) {
        this.titulo = titulo;
    }

}
