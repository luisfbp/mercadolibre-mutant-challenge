package com.mercadolibre.mutantchallenge.service;

import com.mercadolibre.mutantchallenge.dao.DnaCustomRepository;
import com.mercadolibre.mutantchallenge.model.api.DnaPojo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class MutantServiceTest {

    private MutantService mutantService;

    @BeforeEach
    public void setup() {
        mutantService = new MutantService(Mockito.mock(DnaCustomRepository.class));
    }

    @Test
    public void isMutantShouldBeFalseWhenNoneNitrogenousBaseOfDNA() {

        List<String> dna = Arrays.asList(
                "ATACGA",
                "CAATGC",
                "TTGTTT",
                "CGACGG",
                "TCGTCA",
                "TCACTG"
        );

        Assertions.assertFalse(mutantService.isMutant(new DnaPojo(dna)));
    }

    @Test
    public void isMutantShouldBeFalseWhenFindOnlyOneNitrogenousBaseOfDNA() {

        List<String> dna = Arrays.asList(
                "ATACGA",
                "CAATGC",
                "TTATTT",
                "CGACGG",
                "TCGTCA",
                "TCACTG"
        );

        Assertions.assertFalse(mutantService.isMutant(new DnaPojo(dna)));
    }

    @Test
    public void isMutantShouldBeTrueWithVerticalDna() {

        List<String> dna = Arrays.asList(
                "ATACGA",
                "CAATGC",
                "TTATTT",
                "TGACGG",
                "TCGTCA",
                "TCACTG"
        );

        Assertions.assertTrue(mutantService.isMutant(new DnaPojo(dna)));
    }

    @Test
    public void isMutantShouldBeTrueWithHorizontalDna() {

        List<String> dna = Arrays.asList(
                "TTTTGA",
                "CAATGC",
                "TTGTTT",
                "CGACGG",
                "TCGTCA",
                "TCAAAA"
        );

        Assertions.assertTrue(mutantService.isMutant(new DnaPojo(dna)));
    }

    @Test
    public void isMutantShouldBeTrueWithDiagonalDna() {

        List<String> dna = Arrays.asList(
                "ATACGG",
                "CAATGC",
                "TTGGTT",
                "CTGCGG",
                "TCTTCA",
                "TCATTG"
        );

        Assertions.assertTrue(mutantService.isMutant(new DnaPojo(dna)));
    }

    @Test
    public void isMutantShouldBeTrueWithDiagonalAndHorizontalDna() {

        List<String> dna = Arrays.asList(
                "ATACGG",
                "CAATGC",
                "TTGGTT",
                "CAGCGG",
                "TCTTCA",
                "TACCCC"
        );

        Assertions.assertTrue(mutantService.isMutant(new DnaPojo(dna)));
    }

}
