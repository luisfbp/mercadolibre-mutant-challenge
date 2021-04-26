package com.mercadolibre.mutantchallenge.controller;

import com.mercadolibre.mutantchallenge.model.api.DnaPojo;
import com.mercadolibre.mutantchallenge.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Void> isMutant(@RequestBody DnaPojo dnaPojo) {
        return mutantService.isMutant(dnaPojo) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

}
