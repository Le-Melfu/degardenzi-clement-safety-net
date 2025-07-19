package com.safetynet.repository.implementations;

import com.safetynet.config.loader.FakeDatabase;
import com.safetynet.model.MedicalRecord;
import com.safetynet.repository.interfaces.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MedicalRecordInMemoryRepository implements MedicalRecordRepository {

    private final FakeDatabase fakeDatabase;

    public List<MedicalRecord> findAll() {
        return fakeDatabase.getMedicalrecords();
    }

    public MedicalRecord findByFullName(String firstName, String lastName) {
        return fakeDatabase.getMedicalrecords().stream()
                .filter(r -> r.getFirstName().equalsIgnoreCase(firstName)
                        && r.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    public String getBirthdateByFullName(String firstName, String lastName) {
        MedicalRecord record = findByFullName(firstName, lastName);
        return (record != null) ? record.getBirthdate() : null;
    }

    public void createNewMedicalRecord(MedicalRecord record) {
        List<MedicalRecord> current = new ArrayList<>(fakeDatabase.getMedicalrecords());
        boolean exists = current.stream()
                .anyMatch(r -> r.getFirstName().equalsIgnoreCase(record.getFirstName()) &&
                        r.getLastName().equalsIgnoreCase(record.getLastName()));
        if (!exists) {
            current.add(record);
            fakeDatabase.writeMedicalRecordData(current);
        }
    }

    public void updateMedicalRecord(MedicalRecord record) {
        List<MedicalRecord> current = new ArrayList<>(fakeDatabase.getMedicalrecords());
        boolean updated = false;
        for (int i = 0; i < current.size(); i++) {
            MedicalRecord existing = current.get(i);
            if (existing.getFirstName().equalsIgnoreCase(record.getFirstName()) &&
                    existing.getLastName().equalsIgnoreCase(record.getLastName())) {
                current.set(i, record);
                updated = true;
                break;
            }
        }
        if (updated) {
            fakeDatabase.writeMedicalRecordData(current);
        }
    }

    public void deleteMedicalRecordByFullName(String firstName, String lastName) {
        List<MedicalRecord> current = new ArrayList<>(fakeDatabase.getMedicalrecords());
        boolean removed = current.removeIf(r -> r.getFirstName().equalsIgnoreCase(firstName) &&
                r.getLastName().equalsIgnoreCase(lastName));
        if (removed) {
            fakeDatabase.writeMedicalRecordData(current);
        }
    }
}
