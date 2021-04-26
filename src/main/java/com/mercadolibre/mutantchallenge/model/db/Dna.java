package com.mercadolibre.mutantchallenge.model.db;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "dnas")
public class Dna {

    private List<String> dna;

    private int dnaHash;

    @Indexed
    public Type type;

    public enum Type {
        HUMAN,
        MUTANT
    }

    public Dna(List<String> dna, Type type, int dnaHash) {
        this.dnaHash = dnaHash;
        this.dna = dna;
        this.type = type;
    }

    public int getDnaHash() {
        return dnaHash;
    }

    public void setDnaHash(int dnaHash) {
        this.dnaHash = dnaHash;
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
