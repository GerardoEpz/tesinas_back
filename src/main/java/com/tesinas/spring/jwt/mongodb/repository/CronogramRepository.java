package com.tesinas.spring.jwt.mongodb.repository;

import com.tesinas.spring.jwt.mongodb.models.Cronogram;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CronogramRepository extends MongoRepository<Cronogram,String> {

}
