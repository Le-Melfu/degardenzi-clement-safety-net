package com.safetynet.config;

import com.safetynet.service.interfaces.PersonService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonControllerTestConfig {
    @Bean
    public PersonService personService() {
        return Mockito.mock(PersonService.class);
    }
}
