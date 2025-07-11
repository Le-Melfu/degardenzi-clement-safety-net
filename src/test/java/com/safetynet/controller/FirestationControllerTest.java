package com.safetynet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.config.FirestationControllerTestConfig;
import com.safetynet.model.FirestationMapping;
import com.safetynet.model.dto.FirestationCoverageDTO;
import com.safetynet.service.interfaces.FirestationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FirestationController.class)
@Import(FirestationControllerTestConfig.class)
class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FirestationService firestationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getCoverageByStation() throws Exception {
        FirestationCoverageDTO dto = new FirestationCoverageDTO(List.of(), 2, 1);
        when(firestationService.getPersonsCoveredByStation("1")).thenReturn(dto);

        mockMvc.perform(get("/firestation")
                        .param("stationNumber", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void createMapping() throws Exception {
        FirestationMapping mapping = new FirestationMapping("123 Main St", "2");
        when(firestationService.createNewFirestation(any())).thenReturn(true);

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mapping)))
                .andExpect(status().isCreated());
    }

    @Test
    void createMappingConflict() throws Exception {
        FirestationMapping mapping = new FirestationMapping("123 Main St", "2");
        when(firestationService.createNewFirestation(any())).thenReturn(false);

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mapping)))
                .andExpect(status().isConflict());
    }

    @Test
    void updateMapping() throws Exception {
        FirestationMapping mapping = new FirestationMapping("123 Main St", "3");
        when(firestationService.updateFirestation(any())).thenReturn(true);

        mockMvc.perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mapping)))
                .andExpect(status().isOk());
    }

    @Test
    void updateMappingNotFound() throws Exception {
        FirestationMapping mapping = new FirestationMapping("Ghost St", "3");
        when(firestationService.updateFirestation(any())).thenReturn(false);

        mockMvc.perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mapping)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteMapping() throws Exception {
        when(firestationService.deleteFirestationByAddress("123 Main St")).thenReturn(true);

        mockMvc.perform(delete("/firestation")
                        .param("address", "123 Main St"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteMappingNotFound() throws Exception {
        when(firestationService.deleteFirestationByAddress("Ghost St")).thenReturn(false);

        mockMvc.perform(delete("/firestation")
                        .param("address", "Ghost St"))
                .andExpect(status().isNotFound());
    }
}
