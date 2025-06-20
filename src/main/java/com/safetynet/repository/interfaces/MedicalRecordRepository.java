public interface MedicalRecordRepository {

    /**
     * Finds a medical record by first and last name.
     *
     * @param firstName the person's first name
     * @param lastName  the person's last name
     * @return the corresponding MedicalRecord if found, or null otherwise
     */
    MedicalRecord findByFullName(String firstName, String lastName);

    /**
     * Retrieves the birthdate of a person from their medical record.
     *
     * @param firstName the person's first name
     * @param lastName  the person's last name
     * @return the birthdate as a String, or null if not found
     */
    String getBirthdateByFullName(String firstName, String lastName);

    /**
     * Adds a new medical record to the repository.
     *
     * @param record the medical record to add
     */
    void createNewMedicalRecord(MedicalRecord record);

    /**
     * Updates an existing medical record.
     * The record is identified by first and last name.
     *
     * @param record the updated medical record
     */
    void updateMedicalRecord(MedicalRecord record);

    /**
     * Deletes a medical record identified by first and last name.
     *
     * @param firstName the person's first name
     * @param lastName  the person's last name
     */
    void deleteMedicalRecordByFullName(String firstName, String lastName);
}
