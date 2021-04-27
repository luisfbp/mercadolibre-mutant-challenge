package com.mercadolibre.mutantchallenge.repository;

import com.mercadolibre.mutantchallenge.model.db.Dna;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collections;

public class DnaCustomRepositoryTest {

    private MongoTemplate mongoTemplateMock;

    private DnaCustomRepository dnaCustomRepository;

    @BeforeEach
    public void setup() {
        mongoTemplateMock = Mockito.mock(MongoTemplate.class);
        dnaCustomRepository = new DnaCustomRepository(mongoTemplateMock);
    }

    @Test
    public void shouldNotSaveIfTheMatrixAlreadyExistInTheDB() {

        Dna dna = new Dna(Collections.emptyList(), Dna.Type.MUTANT, "Testing".hashCode());
        Mockito.when(mongoTemplateMock.findOne(new Query(), Dna.class)).thenReturn(dna);

        dnaCustomRepository.saveDna(dna);
        Mockito.verify(mongoTemplateMock, Mockito.timeout(0)).save(dna);
    }

    @Test
    public void shouldSaveIfTheMatrixDoesNotExistInTheDB() {

        Mockito.when(mongoTemplateMock.findOne(new Query(), Dna.class)).thenReturn(null);

        Dna dna = new Dna(Collections.emptyList(), Dna.Type.MUTANT, "Testing".hashCode());
        dnaCustomRepository.saveDna(dna);

        Mockito.verify(mongoTemplateMock, Mockito.timeout(1)).save(Mockito.any());
    }

}
