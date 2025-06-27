package com.safetynet.service.interfaces;

import com.safetynet.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {
    MedicalRecord getMedicalRecordByFullName(String firstName, String lastName);

    String getBirthdate(String firstName, String lastName);

    List<String> getMedications(String firstName, String lastName);

    List<String> getAllergies(String firstName, String lastName);

    boolean addMedicalRecord(MedicalRecord record);

    boolean updateMedicalRecord(MedicalRecord record);

    boolean deleteMedicalRecord(String firstName, String lastName);

    int calculateAge(String birthdate);

}
