package com.safetynet.config.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safetynet.model.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class JsonDataWriter {

    @Value("${data.output.path:./src/main/resources/data/data.json}")
    private String outputPath;

    public void saveData(Data dataToSave) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            File outputFile = new File(outputPath);
            mapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, dataToSave);

            log.info("[SAVE] JSON data saved successfully to {}", outputPath);
        } catch (Exception e) {
            log.error("[ERROR] Failed to save JSON data: {}", e.getMessage(), e);
        }
    }
}
