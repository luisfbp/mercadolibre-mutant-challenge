package com.mercadolibre.mutantchallenge.controller;

import com.mercadolibre.mutantchallenge.model.api.StatsResponse;
import com.mercadolibre.mutantchallenge.service.StatsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatsController.class)
public class StatsControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatsService statsService;

    @Test
    public void shouldResponseOk() throws Exception {
        Mockito.when(statsService.calculateStats()).thenReturn(new StatsResponse());
        mockMvc.perform(get("/stats").contentType(APPLICATION_JSON_UTF8)).andExpect(status().isOk());
    }

}
