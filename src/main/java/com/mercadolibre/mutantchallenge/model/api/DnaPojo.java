package com.mercadolibre.mutantchallenge.model.api;

import java.util.List;

/**
 * Request pojo which carry DNA information.
 */
public class DnaPojo {

    private List<String> dna;

    public DnaPojo() {
        //Default constructor
    }

    public DnaPojo(List<String> dna) {
        this.dna = dna;
    }

    public List<String> getDna() {
        return dna;
    }

    public void setDna(List<String> dna) {
        this.dna = dna;
    }
}
