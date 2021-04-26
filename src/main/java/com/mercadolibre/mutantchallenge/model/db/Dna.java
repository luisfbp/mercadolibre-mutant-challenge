package com.mercadolibre.mutantchallenge.model.db;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Dna {

    @Id
    public String id;

    public List<String> dna;
    public Type type;

    public enum Type {
        HUMAN,
        MUTANT
    }

    public Dna(List<String> dna, Type type) {
        this.dna = dna;
        this.type = type;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getDna() {
        return dna;
    }

    public void setDna(List<String> dna) {
        this.dna = dna;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
