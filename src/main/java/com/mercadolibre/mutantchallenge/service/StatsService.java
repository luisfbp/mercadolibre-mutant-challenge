package com.mercadolibre.mutantchallenge.service;

import com.mercadolibre.mutantchallenge.model.api.StatsResponse;
import com.mercadolibre.mutantchallenge.repository.DnaCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final DnaCustomRepository customRepository;

    @Autowired
    public StatsService(DnaCustomRepository customRepository) {
        this.customRepository = customRepository;
    }

    public StatsResponse calculateStats() {
        StatsResponse statsResponse = customRepository.findSumResult();

        if (statsResponse != null ) {
            statsResponse.setRatio((double) (statsResponse.getCountMutantDna() / statsResponse.getCountHumanDna()));
        }
        return statsResponse;
    }

}
