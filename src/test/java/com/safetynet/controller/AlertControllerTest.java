package com.safetynet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.config.AlertControllerTestConfig;
import com.safetynet.model.dto.ChildAlertDTO;
import com.safetynet.model.dto.FireIncidentDTO;
import com.safetynet.model.dto.PersonWithMedicalDataDTO;
import com.safetynet.model.dto.StationFloodCoverageDTO;
import com.safetynet.service.interfaces.AlertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlertController.class)
@Import(AlertControllerTestConfig.class)
class AlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlertService alertService;

    @Test
    void getChildrenByAddress_shouldReturnDTO() throws Exception {
        ChildAlertDTO dto = new ChildAlertDTO(List.of(), List.of());
        when(alertService.getChildrenByAddress("TestAddress")).thenReturn(dto);

        mockMvc.perform(get("/childAlert?address=TestAddress"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(dto)));
    }

    @Test
    void getPhoneNumbersByStation_shouldReturnPhones() throws Exception {
        when(alertService.getPhoneNumbersByStation("1")).thenReturn(List.of("1234567890"));

        mockMvc.perform(get("/phoneAlert?stationNumber=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("1234567890"));
    }

    @Test
    void getPhoneNumbersByStation_shouldReturnNoContent() throws Exception {
        when(alertService.getPhoneNumbersByStation("99")).thenReturn(List.of());

        mockMvc.perform(get("/phoneAlert?stationNumber=99"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getFireIncident_shouldReturnDTO() throws Exception {
        FireIncidentDTO dto = new FireIncidentDTO("1", List.of());
        when(alertService.getFireIncidentByAddress("TestAddress")).thenReturn(dto);

        mockMvc.perform(get("/fire?address=TestAddress"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stationNumber").value("1"));
    }

    @Test
    void getFireIncident_shouldReturnNotFound() throws Exception {
        when(alertService.getFireIncidentByAddress("Unknown")).thenReturn(null);

        mockMvc.perform(get("/fire?address=Unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getFloodCoverage_shouldReturnData() throws Exception {
        when(alertService.getStationsFloodCoverage(List.of("1"))).thenReturn(List.of(new StationFloodCoverageDTO("1", List.of())));

        mockMvc.perform(get("/flood/stations?stations=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].stationNumber").value("1"));
    }

    @Test
    void getFloodCoverage_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/flood/stations?stations="))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPersonInfosByLastName_shouldReturnList() throws Exception {
        when(alertService.getPersonsInfosByLastName("Doe"))
                .thenReturn(List.of(new PersonWithMedicalDataDTO("John", "Doe", "12345", 30, List.of(), List.of())));

        mockMvc.perform(get("/personInfosLastName?lastName=Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    void getPersonInfosByLastName_shouldReturnNotFound() throws Exception {
        when(alertService.getPersonsInfosByLastName("Ghost")).thenReturn(List.of());

        mockMvc.perform(get("/personInfosLastName?lastName=Ghost"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getEmailsByCity_shouldReturnList() throws Exception {
        when(alertService.getEmailsByCity("Culver")).thenReturn(List.of("a@b.com"));

        mockMvc.perform(get("/communityEmail?city=Culver"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("a@b.com"));
    }

    @Test
    void getEmailsByCity_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/communityEmail?city="))
                .andExpect(status().isBadRequest());
    }
}
