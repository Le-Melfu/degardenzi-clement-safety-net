package com.safetynet.service.interfaces;

import com.safetynet.model.MedicalRecord;

/**
 * MedicalRecordService provides operations for accessing, managing,
 * and processing individuals' medical records.
 */
public interface MedicalRecordService {

    /**
     * Retrieves the medical record for a person identified by their full name.
     *
     * @param firstName the person's first name
     * @param lastName  the person's last name
     * @return the {@link MedicalRecord} for the person, or null if not found
     */
    MedicalRecord getMedicalRecordByFullName(String firstName, String lastName);

    /**
     * Retrieves the birthdate for a person identified by their full name.
     *
     * @param firstName the person's first name
     * @param lastName  the person's last name
     * @return the birthdate as a String in format "MM/dd/yyyy", or null if not found
     */
    String getBirthdate(String firstName, String lastName);

    /**
     * Adds a new medical record to the system.
     *
     * @param record the {@link MedicalRecord} to add
     * @return true if the record was added successfully, false otherwise
     */
    boolean addMedicalRecord(MedicalRecord record);

    /**
     * Updates an existing medical record.
     *
     * @param record the {@link MedicalRecord} with updated data
     * @return true if the record was updated successfully, false otherwise
     */
    boolean updateMedicalRecord(MedicalRecord record);

    /**
     * Deletes the medical record of a person identified by their full name.
     *
     * @param firstName the person's first name
     * @param lastName  the person's last name
     * @return true if the record was deleted successfully, false otherwise
     */
    boolean deleteMedicalRecord(String firstName, String lastName);

    /**
     * Calculates the age of a person based on their birthdate.
     *
     * @param birthdate the birthdate in format "MM/dd/yyyy"
     * @param firstName the person's first name (used for logging or error context)
     * @param lastName  the person's last name (used for logging or error context)
     * @return the calculated age in years
     */
    int calculateAge(String birthdate, String firstName, String lastName);
}
