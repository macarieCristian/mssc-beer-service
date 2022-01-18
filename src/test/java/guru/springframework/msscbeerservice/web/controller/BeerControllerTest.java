package guru.springframework.msscbeerservice.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(get("/api/v1/beerss/" + UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void saveBeer() throws Exception {
        mockMvc.perform(post("/api/v1/beer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getValidBeerDto())))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateBeer() throws Exception {
        mockMvc.perform(put("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getValidBeerDto())))
                .andExpect(status().isNoContent());
    }

    private static BeerDto getValidBeerDto() {
        return BeerDto.builder()
                .beerName("Mine")
                .beerStyle(BeerStyleEnum.ALE)
                .price(new BigDecimal("2.99"))
                .upc(123124214L)
                .build();
    }
}