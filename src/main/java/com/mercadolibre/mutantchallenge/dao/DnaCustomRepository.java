package com.mercadolibre.mutantchallenge.dao;

import com.mercadolibre.mutantchallenge.model.db.Dna;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class DnaCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public DnaCustomRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Save the given {@link Dna} only if it does not exist already.
     * @param dna {@link Dna} object to save.
     */
    public void saveDna(Dna dna) {

        Query query = new Query(Criteria.where("dnaHash").is(dna.getDnaHash()));
        Dna existingDna = mongoTemplate.findOne(query, Dna.class);

        if (existingDna == null) {
            mongoTemplate.save(dna);
        }

    }
}
