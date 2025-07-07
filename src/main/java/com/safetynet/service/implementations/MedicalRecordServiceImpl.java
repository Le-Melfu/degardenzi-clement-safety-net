package com.safetynet.service.implementations;

import com.safetynet.model.MedicalRecord;
import com.safetynet.repository.interfaces.MedicalRecordRepository;
import com.safetynet.service.interfaces.MedicalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @Override
    public MedicalRecord getMedicalRecordByFullName(String firstName, String lastName) {
        return medicalRecordRepository.findByFullName(firstName, lastName);
    }

    @Override
    public String getBirthdate(String firstName, String lastName) {
        return medicalRecordRepository.getBirthdateByFullName(firstName, lastName);
    }

    @Override
    public boolean addMedicalRecord(MedicalRecord record) {
        if (medicalRecordRepository.findByFullName(record.getFirstName(), record.getLastName()) == null) {
            medicalRecordRepository.createNewMedicalRecord(record);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateMedicalRecord(MedicalRecord record) {
        if (medicalRecordRepository.findByFullName(record.getFirstName(), record.getLastName()) != null) {
            medicalRecordRepository.updateMedicalRecord(record);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        if (medicalRecordRepository.findByFullName(firstName, lastName) != null) {
            medicalRecordRepository.deleteMedicalRecordByFullName(firstName, lastName);
            return true;
        }
        return false;
    }

    @Override
    public int calculateAge(String birthdate, String firstName, String lastName) {
        if (birthdate == null || birthdate.isBlank()) {
            log.info("Birthdate is missing for this person: {} {}", firstName, lastName);
            return 0;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate birth = LocalDate.parse(birthdate, formatter);
            return Period.between(birth, LocalDate.now()).getYears();
        } catch (DateTimeParseException e) {
            log.error("Unable to parse birthdate '{}' for user {} {}: {}", birthdate, firstName, lastName, e.getMessage());
            return 0;
        }
    }
}
