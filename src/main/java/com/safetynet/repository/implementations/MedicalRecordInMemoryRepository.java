package com.safetynet.repository.implementations;

import com.safetynet.model.MedicalRecord;
import com.safetynet.repository.interfaces.MedicalRecordRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalRecordInMemoryRepository implements MedicalRecordRepository {

    private final List<MedicalRecord> records = new ArrayList<>();

    public void setData(List<MedicalRecord> data) {
        records.clear();
        records.addAll(data);
    }

    @Override
    public MedicalRecord findByFullName(String firstName, String lastName) {
        return records.stream()
                .filter(r -> r.getFirstName().equalsIgnoreCase(firstName)
                        && r.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String getBirthdateByFullName(String firstName, String lastName) {
        MedicalRecord record = findByFullName(firstName, lastName);
        return record != null ? record.getBirthdate() : null;
    }

    @Override
    public void createNewMedicalRecord(MedicalRecord record) {
        boolean exists = records.stream()
                .anyMatch(r -> r.getFirstName().equalsIgnoreCase(record.getFirstName())
                        && r.getLastName().equalsIgnoreCase(record.getLastName()));
        if (!exists) {
            records.add(record);
        }
    }

    @Override
    public void updateMedicalRecord(MedicalRecord record) {
        for (int i = 0; i < records.size(); i++) {
            MedicalRecord existing = records.get(i);
            if (existing.getFirstName().equalsIgnoreCase(record.getFirstName()) &&
                    existing.getLastName().equalsIgnoreCase(record.getLastName())) {
                records.set(i, record);
                return;
            }
        }
    }

    @Override
    public void deleteMedicalRecordByFullName(String firstName, String lastName) {
        records.removeIf(r -> r.getFirstName().equalsIgnoreCase(firstName)
                && r.getLastName().equalsIgnoreCase(lastName));
    }
}
