package com.team4.artgallery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ArtworkRestTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Rollback
    void testArtworkGetOk() throws Exception {
        mockMvc.perform(get("/artwork/10").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Rollback
    void testArtworkGetNotFound() throws Exception {
        mockMvc.perform(get("/artwork/0").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Rollback
    void testArtworkGetBadRequest() throws Exception {
        mockMvc.perform(get("/artwork/asdf").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
