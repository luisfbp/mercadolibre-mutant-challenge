package com.mercadolibre.mutantchallenge.controller;

import com.mercadolibre.mutantchallenge.service.MutantService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MutantController.class)
public class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MutantService mutantService;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Test
    public void shouldReturnABadRequest() throws Exception {
        Mockito.when(mutantService.isMutant(Mockito.any())).thenReturn(false);

        mockMvc.perform(post("/mutant").contentType(APPLICATION_JSON_UTF8).content(payload())).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnAOk() throws Exception {
        Mockito.when(mutantService.isMutant(Mockito.any())).thenReturn(true);

        mockMvc.perform(post("/mutant").contentType(APPLICATION_JSON_UTF8).content(payload())).andExpect(status().isOk());
    }

    public String payload() {
        return "{\n" +
                "    \"dna\":[\"TTGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]\n" +
                "}";
    }

}
