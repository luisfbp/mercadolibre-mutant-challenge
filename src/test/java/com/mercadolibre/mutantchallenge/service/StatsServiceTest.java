package com.mercadolibre.mutantchallenge.service;

import com.mercadolibre.mutantchallenge.model.api.StatsResponse;
import com.mercadolibre.mutantchallenge.repository.DnaCustomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class StatsServiceTest {

    private StatsService statsService;

    private DnaCustomRepository dnaCustomRepositoryMock;

    @BeforeEach()
    public void setup() {
        dnaCustomRepositoryMock = Mockito.mock(DnaCustomRepository.class);
        this.statsService = new StatsService(dnaCustomRepositoryMock);
    }

    @Test
    public void shouldReturnNullWhenNotResultReturn() {
        Mockito.when(dnaCustomRepositoryMock.findSumResult()).thenReturn(null);
        Assertions.assertNull(statsService.calculateStats());
    }

    @Test
    public void shouldCalculateRatio() {
        StatsResponse statsResponse = new StatsResponse();
        statsResponse.setCountHumanDna(100);
        statsResponse.setCountMutantDna(40);
        Mockito.when(dnaCustomRepositoryMock.findSumResult()).thenReturn(statsResponse);

        Assertions.assertEquals(0.4, statsService.calculateStats().getRatio());
    }

}
