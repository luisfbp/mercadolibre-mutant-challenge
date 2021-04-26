package com.mercadolibre.mutantchallenge.dao;

import com.mercadolibre.mutantchallenge.model.db.Dna;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DnaDao extends MongoRepository<Dna, String> {
}
