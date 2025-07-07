package com.safetynet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.config.MedicalRecordControllerTestConfig;
import com.safetynet.model.MedicalRecord;
import com.safetynet.service.interfaces.MedicalRecordService;
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

@WebMvcTest(MedicalRecordController.class)
@Import(MedicalRecordControllerTestConfig.class)
class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MedicalRecordService medicalRecordService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MedicalRecord sampleRecord = new MedicalRecord(
            "John", "Doe", "01/01/1990",
            List.of("med1:500mg"), List.of("peanut")
    );

    @Test
    void createMedicalRecord_shouldReturn201_whenCreated() throws Exception {
        when(medicalRecordService.addMedicalRecord(any())).thenReturn(true);

        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRecord)))
                .andExpect(status().isCreated());
    }

    @Test
    void createMedicalRecord_shouldReturn409_whenExists() throws Exception {
        when(medicalRecordService.addMedicalRecord(any())).thenReturn(false);

        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRecord)))
                .andExpect(status().isConflict());
    }

    @Test
    void updateMedicalRecord_shouldReturn200_whenUpdated() throws Exception {
        when(medicalRecordService.updateMedicalRecord(any())).thenReturn(true);

        mockMvc.perform(put("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRecord)))
                .andExpect(status().isOk());
    }

    @Test
    void updateMedicalRecord_shouldReturn404_whenNotFound() throws Exception {
        when(medicalRecordService.updateMedicalRecord(any())).thenReturn(false);

        mockMvc.perform(put("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRecord)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteMedicalRecord_shouldReturn204_whenDeleted() throws Exception {
        when(medicalRecordService.deleteMedicalRecord("John", "Doe")).thenReturn(true);

        mockMvc.perform(delete("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteMedicalRecord_shouldReturn404_whenNotFound() throws Exception {
        when(medicalRecordService.deleteMedicalRecord("Ghost", "Guy")).thenReturn(false);

        mockMvc.perform(delete("/medicalRecord")
                        .param("firstName", "Ghost")
                        .param("lastName", "Guy"))
                .andExpect(status().isNotFound());
    }
}
