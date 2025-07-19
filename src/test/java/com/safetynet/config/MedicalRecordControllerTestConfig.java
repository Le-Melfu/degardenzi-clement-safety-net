package com.safetynet.config;

import com.safetynet.service.interfaces.MedicalRecordService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MedicalRecordControllerTestConfig {

    @Bean
    public MedicalRecordService medicalRecordService() {
        return Mockito.mock(MedicalRecordService.class);
    }
}
