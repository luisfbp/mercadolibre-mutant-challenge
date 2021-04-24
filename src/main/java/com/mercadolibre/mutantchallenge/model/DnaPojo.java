package com.mercadolibre.mutantchallenge.model;

import java.util.List;

/**
 * Request pojo which carry DNA information.
 */
public class DnaPojo {

    private List<String> dna;

    public List<String> getDna() {
        return dna;
    }

    public void setDna(List<String> dna) {
        this.dna = dna;
    }
}
