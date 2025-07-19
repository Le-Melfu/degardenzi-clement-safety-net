package com.safetynet.repository.implementations;

import com.safetynet.config.loader.FakeDatabase;
import com.safetynet.model.FirestationMapping;
import com.safetynet.repository.interfaces.FirestationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FirestationInMemoryRepository implements FirestationRepository {

    private final FakeDatabase fakeDatabase;

    public List<FirestationMapping> findAll() {
        return fakeDatabase.getFirestationMappings();
    }

    public FirestationMapping findByAddress(String address) {
        return fakeDatabase.getFirestationMappings().stream()
                .filter(f -> f.getAddress().equalsIgnoreCase(address))
                .findFirst()
                .orElse(null);
    }

    public List<String> getStationAdresses(String station) {
        return fakeDatabase.getFirestationMappings().stream()
                .filter(f -> f.getStation().equals(station))
                .map(FirestationMapping::getAddress)
                .toList();
    }

    public void createNewFirestationMapping(FirestationMapping firestationMapping) {
        List<FirestationMapping> current = new ArrayList<>(fakeDatabase.getFirestationMappings());
        boolean exists = current.stream()
                .anyMatch(f -> f.getAddress().equalsIgnoreCase(firestationMapping.getAddress()));
        if (!exists) {
            current.add(firestationMapping);
            fakeDatabase.writeFirestationsData(current);
        }
    }

    public void updateFirestationMapping(FirestationMapping firestationMapping) {
        List<FirestationMapping> current = new ArrayList<>(fakeDatabase.getFirestationMappings());
        boolean updated = false;
        for (int i = 0; i < current.size(); i++) {
            FirestationMapping existing = current.get(i);
            if (existing.getAddress().equalsIgnoreCase(firestationMapping.getAddress())) {
                current.set(i, firestationMapping);
                updated = true;
                break;
            }
        }
        if (updated) {
            fakeDatabase.writeFirestationsData(current);
        }
    }

    public void deleteFirestationMappingByAddress(String address) {
        List<FirestationMapping> current = new ArrayList<>(fakeDatabase.getFirestationMappings());
        boolean removed = current.removeIf(f -> f.getAddress().equalsIgnoreCase(address));
        if (removed) {
            fakeDatabase.writeFirestationsData(current);
        }
    }
}
