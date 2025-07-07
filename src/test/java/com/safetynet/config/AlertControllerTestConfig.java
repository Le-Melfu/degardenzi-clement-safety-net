package com.safetynet.config;

import com.safetynet.service.interfaces.AlertService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlertControllerTestConfig {

    @Bean
    public AlertService firestationService() {
        return Mockito.mock(AlertService.class);
    }
}
