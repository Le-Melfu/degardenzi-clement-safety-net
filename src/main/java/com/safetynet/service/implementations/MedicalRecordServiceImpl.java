package com.safetynet.service.implementations;

import com.safetynet.model.MedicalRecord;
import com.safetynet.repository.interfaces.MedicalRecordRepository;
import com.safetynet.service.interfaces.MedicalRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    public List<String> getMedications(String firstName, String lastName) {
        MedicalRecord record = getMedicalRecordByFullName(firstName, lastName);
        return record != null ? record.getMedications() : List.of();
    }

    @Override
    public List<String> getAllergies(String firstName, String lastName) {
        MedicalRecord record = getMedicalRecordByFullName(firstName, lastName);
        return record != null ? record.getAllergies() : List.of();
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
    public int calculateAge(String birthdate) {
        if (birthdate == null || birthdate.isBlank()) {
            throw new IllegalArgumentException("Birthdate is missing for this person");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birth = LocalDate.parse(birthdate, formatter);
        return Period.between(birth, LocalDate.now()).getYears();
    }
}
