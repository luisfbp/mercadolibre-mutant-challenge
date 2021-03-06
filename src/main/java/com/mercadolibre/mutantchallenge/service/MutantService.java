package com.mercadolibre.mutantchallenge.service;

import com.mercadolibre.mutantchallenge.exception.ApiThrowable;
import com.mercadolibre.mutantchallenge.repository.DnaCustomRepository;
import com.mercadolibre.mutantchallenge.model.api.DnaPayload;
import com.mercadolibre.mutantchallenge.model.db.Dna;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(MutantService.class);

    private final DnaCustomRepository dnaCustomRepository;

    @Autowired
    public MutantService(DnaCustomRepository dnaCustomRepository) {
        this.dnaCustomRepository = dnaCustomRepository;
    }

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
     * Gets the DNA matrix from a given {@link DnaPayload} and checks if there is more than one nitrogenous base of DNA.
     *
     * @param dnaPayload Object to extract the dna from.
     * @return {@code true} if there is at least 2 nitrogenous base of DNA in the given matrix.
     */
    public boolean isMutant(DnaPayload dnaPayload) {
        String[][] dna = dnaPayload.getDna().stream().map(dnaRow -> dnaRow.split("")).collect(Collectors.toList()).toArray(String[][]::new);

        validateMatrix(dnaPayload.getDna());

        int countMutantDna = 0;
        for (int r = 0; r < dna.length; r++) {
            for (int c = 0; c < dna[r].length; c++) {
                if (findMutantDna(dna, r, c, dna[r][c])) {
                    countMutantDna += 1;
                    if (countMutantDna >= 2) {
                        saveDnaAsync(new Dna(dnaPayload.getDna(), Dna.Type.MUTANT, dnaPayload.getDna().hashCode()));
                        return true;
                    }
                }
            }
        }
        saveDnaAsync(new Dna(dnaPayload.getDna(), Dna.Type.HUMAN, dnaPayload.getDna().hashCode()));
        return false;
    }

    /**
     * Checks if the size and letters in the matrix are correct.
     * @param dnaMatrix DNA matrix to check.
     */
    private void validateMatrix(List<String> dnaMatrix) {
        dnaMatrix.forEach(dnaChain -> {
            if (dnaMatrix.size() != dnaChain.length()) {
                String message = "DNA matrix must be square.";
                LOGGER.warn(message);
                throw new ApiThrowable(message, HttpStatus.BAD_REQUEST);
            }
            if (!dnaChain.matches("[ATCG]*")) {
                String message = "The matrix can only contain the letters A T C and G.";
                LOGGER.warn(message);
                throw new ApiThrowable(message, HttpStatus.BAD_REQUEST);
            }
        });


    }

    /**
     * Save the given ADN into the data base async.
     * @param dna dna to save.
     */
    private void saveDnaAsync(Dna dna) {
        CompletableFuture.runAsync(() -> dnaCustomRepository.saveDna(dna));
    }

    /**
     * Goes through all possible directions searching for a mutant DNA.
     *
     * @param dna    DNA matrix.
     * @param r      Current row.
     * @param c      Current column.
     * @param letter Current letter to check.
     * @return {@code true} if finds at least one mutant DNA
     */
    private boolean findMutantDna(String[][] dna, int r, int c, String letter) {

        return findMutant(dna, r, c, 0, letter, Direction.RIGHT) ||
                findMutant(dna, r, c, 0, letter, Direction.DOWN) ||
                findMutant(dna, r, c, 0, letter, Direction.DIAGONAL_RIGHT_DOWN) ||
                findMutant(dna, r, c, 0, letter, Direction.DIAGONAL_LEFT_DOWN);
    }

    /**
     * Improve performance checking if for a given [column, row] there is need to validate the given direction.
     *
     * @param dna       DNA matrix
     * @param r         current row
     * @param c         current column
     * @param direction {@link Direction} to check.
     * @return {@code true} if the current position at the matrix is valid for checking.
     */
    private boolean shouldValidate(String[][] dna, int r, int c, Direction direction) {

        int rightRemaining = dna[r].length - c;
        int downRemaining = dna.length - r;

        // Check if the current row column is at the corner of the matrix
        if (rightRemaining < DNA_LENGTH && downRemaining < DNA_LENGTH) return false;

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
     * @param dna       DNA matrix.
     * @param r         Row index.
     * @param c         Column index.
     * @param count     Times of repeated letters consecutively.
     * @param letter    Current letter to check.
     * @param direction Direction to follow when going over the matrix.
     * @return {@code true} if finds a letter repeated letters consecutively more or equals to {@link MutantService#DNA_LENGTH}
     */
    private boolean findMutant(String[][] dna, int r, int c, int count, String letter, Direction direction) {

        if (count == DNA_LENGTH) {
            return true;
        }

        if (r >= dna.length || c < 0 || c >= dna[r].length || !letter.equals(dna[r][c])) {
            return false;
        }

        // Checks for performance improvements
        if (count == 0 && !shouldValidate(dna, r, c, direction)) {
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
