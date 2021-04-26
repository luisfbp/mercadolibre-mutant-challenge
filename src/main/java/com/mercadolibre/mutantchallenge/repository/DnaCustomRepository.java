package com.mercadolibre.mutantchallenge.repository;

import com.mercadolibre.mutantchallenge.model.api.StatsResponse;
import com.mercadolibre.mutantchallenge.model.db.Dna;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
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

    public StatsResponse findSumResult() {

        ConditionalOperators.Cond conditionalMutants = ConditionalOperators
                .when(ComparisonOperators.valueOf("type").equalToValue(Dna.Type.MUTANT)).then(1).otherwise(0);

        ConditionalOperators.Cond conditionalHumans = ConditionalOperators
                .when(ComparisonOperators.valueOf("type").equalToValue(Dna.Type.HUMAN)).then(1).otherwise(0);

        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group()
                .sum(conditionalMutants).as("countMutantDna")
                .sum(conditionalHumans).as("countHumanDna"));

        return this.mongoTemplate.aggregate(aggregation, Dna.class, StatsResponse.class).getUniqueMappedResult();
    }
}
