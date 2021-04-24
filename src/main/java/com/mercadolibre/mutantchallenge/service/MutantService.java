package com.mercadolibre.mutantchallenge.service;

import com.mercadolibre.mutantchallenge.model.DnaPojo;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * handles all business logic related with mutants
 */
@Service
public class MutantService {

    /**
     * Max DNA length
     */
    private static final int DNA_LENGTH = 4;

    /**
     * Matrix possible directions
     */
    enum Direction {
        RIGHT,
        DOWN,
        DIAGONAL_RIGHT_DOWN,
        DIAGONAL_LEFT_DOWN
    }

    /**
     * Gets the DNA matrix from a given {@link DnaPojo} and checks if there is more than one nitrogenous base of DNA.
     * @param dnaPojo Object to extract the dna from.
     * @return {@code true} if there is at least 2 nitrogenous base of DNA in the given matrix.
     */
    public boolean isMutant(DnaPojo dnaPojo) {
        String[][] dna = dnaPojo.getDna().stream().map(dnaRow -> dnaRow.split("")).collect(Collectors.toList()).toArray(String[][]::new);

        int countMutantDna = 0;
        for (int r = 0; r < dna.length; r++) {
            for (int c = 0; c < dna[r].length; c++) {
                if (findMutantDna(dna, r, c, 0, dna[r][c])) {
                    countMutantDna += 1;
                    if (countMutantDna > 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean findMutantDna(String[][] dna, int r, int c, int count, String letter) {

        int rightRemaining = dna[r].length - c;
        int downRemaining = dna.length - r;

        if (rightRemaining < DNA_LENGTH && downRemaining < DNA_LENGTH) return false;

        return (shouldTestDirection(rightRemaining, downRemaining, c, Direction.RIGHT) && findMutant(dna, r, c, count, letter, Direction.RIGHT)) ||
               (shouldTestDirection(rightRemaining, downRemaining, c, Direction.DOWN) && findMutant(dna, r, c, count, letter, Direction.DOWN)) ||
               (shouldTestDirection(rightRemaining, downRemaining, c, Direction.DIAGONAL_RIGHT_DOWN) && findMutant(dna, r, c, count, letter, Direction.DIAGONAL_RIGHT_DOWN)) ||
               (shouldTestDirection(rightRemaining, downRemaining, c, Direction.DIAGONAL_LEFT_DOWN) && findMutant(dna, r, c, count, letter, Direction.DIAGONAL_LEFT_DOWN));
    }

    private boolean shouldTestDirection(int rightRemaining, int downRemaining, int c, Direction direction) {
        switch (direction) {
            case RIGHT:
                return rightRemaining >= DNA_LENGTH;
            case DOWN:
                return downRemaining >= DNA_LENGTH;
            case DIAGONAL_RIGHT_DOWN:
                return rightRemaining >= DNA_LENGTH && downRemaining >= DNA_LENGTH;
            case DIAGONAL_LEFT_DOWN:
                return c + 1 >= DNA_LENGTH && downRemaining >= DNA_LENGTH;
            default:
                return false;
        }
    }

    /**
     * Recursive method to go over the dna matrix and find repeated letters consecutively in different {@link Direction}s.
     *
     * @param dna DNA matrix.
     * @param r Row index.
     * @param c Column index.
     * @param count Times of repeated letters consecutively.
     * @param letter Current letter to check.
     * @param direction Direction to follow when going over the matrix.
     * @return {@code true} if finds a letter repeated letters consecutively more than {@link MutantService#DNA_LENGTH}
     */
    private boolean findMutant(String[][] dna, int r, int c, int count, String letter, Direction direction) {
        if (count == DNA_LENGTH) {
            return true;
        }

        if (r < 0 || r >= dna.length || c < 0 || c >= dna[r].length || !letter.equals(dna[r][c])) {
            return false;
        }

        if (direction.equals(Direction.RIGHT)) {
            return findMutant(dna, r, c + 1, count + 1, letter, direction);
        }
        if (direction.equals(Direction.DOWN)) {
            return findMutant(dna, r + 1, c, count + 1, letter, direction);
        }
        if (direction.equals(Direction.DIAGONAL_RIGHT_DOWN)) {
            return findMutant(dna, r + 1, c + 1, count + 1, letter, direction);
        }
        if (direction.equals(Direction.DIAGONAL_LEFT_DOWN)) {
            return findMutant(dna, r + 1, c - 1, count + 1, letter, direction);
        }

        return false;
    }

}