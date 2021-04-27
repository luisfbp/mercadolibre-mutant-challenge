package com.mercadolibre.mutantchallenge.service;

import com.mercadolibre.mutantchallenge.exception.ApiThrowable;
import com.mercadolibre.mutantchallenge.repository.DnaCustomRepository;
import com.mercadolibre.mutantchallenge.model.api.DnaPayload;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.JUnitException;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

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

        Assertions.assertFalse(mutantService.isMutant(new DnaPayload(dna)));
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

        Assertions.assertFalse(mutantService.isMutant(new DnaPayload(dna)));
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

        Assertions.assertTrue(mutantService.isMutant(new DnaPayload(dna)));
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

        Assertions.assertTrue(mutantService.isMutant(new DnaPayload(dna)));
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

        Assertions.assertTrue(mutantService.isMutant(new DnaPayload(dna)));
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

        Assertions.assertTrue(mutantService.isMutant(new DnaPayload(dna)));
    }

    @Test
    public void isMutantShouldFailWhenTheMatrixIsNotSquare() {

        List<String> dna = Arrays.asList(
                "ATACGA",
                "CAATGC",
                "TTGTTT",
                "CGACGG",
                "TCGA",
                "TCACTG"
        );

        ApiThrowable apiThrowable = Assertions.assertThrows(ApiThrowable.class, () -> mutantService.isMutant(new DnaPayload(dna)));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, apiThrowable.getHttpStatus());

    }

    @Test
    public void isMutantShouldFailWhenThereIsNotAllowedLettersInTheMatrix() {

        List<String> dna = Arrays.asList(
                "ATACGA",
                "CAATGC",
                "TTGTTT",
                "CGACGG",
                "TCGAGG",
                "TCACTZ"
        );

        ApiThrowable apiThrowable = Assertions.assertThrows(ApiThrowable.class, () -> mutantService.isMutant(new DnaPayload(dna)));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, apiThrowable.getHttpStatus());

    }

}
