package com.tesinas.spring.jwt.mongodb.repository;

import com.tesinas.spring.jwt.mongodb.models.Tesinas;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TesinaRepository extends MongoRepository<Tesinas,String> {

}
