package com.mercadolibre.mutantchallenge.controller;

import com.mercadolibre.mutantchallenge.model.api.DnaPayload;
import com.mercadolibre.mutantchallenge.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Will resolve all calls for mutant endpoints
 */
@Controller
public class MutantController {

    @Autowired
    private MutantService mutantService;

    @PostMapping("/mutant")
    public ResponseEntity<Void> isMutant(@RequestBody DnaPayload dnaPayload) {
        return mutantService.isMutant(dnaPayload) ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
