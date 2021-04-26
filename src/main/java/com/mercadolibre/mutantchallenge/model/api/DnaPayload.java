package com.mercadolibre.mutantchallenge.model.api;

import java.util.List;

/**
 * Request pojo which carry DNA information.
 */
public class DnaPayload {

    private List<String> dna;

    public DnaPayload() {
        //Default constructor
    }

    public DnaPayload(List<String> dna) {
        this.dna = dna;
    }

    public List<String> getDna() {
        return dna;
    }

    public void setDna(List<String> dna) {
        this.dna = dna;
    }
}
