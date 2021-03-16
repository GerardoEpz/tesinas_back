package com.tesinas.spring.jwt.mongodb.repository;

import com.tesinas.spring.jwt.mongodb.models.Faq;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FaqRepository extends MongoRepository<Faq,String> {

}
