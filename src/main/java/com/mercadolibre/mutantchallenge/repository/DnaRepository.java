package com.mercadolibre.mutantchallenge.repository;

import com.mercadolibre.mutantchallenge.model.db.Dna;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DnaRepository extends MongoRepository<Dna, String> {
}
