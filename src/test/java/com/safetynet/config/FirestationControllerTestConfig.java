package com.safetynet.config;

import com.safetynet.service.interfaces.FirestationService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirestationControllerTestConfig {

    @Bean
    public FirestationService firestationService() {
        return Mockito.mock(FirestationService.class);
    }
}
