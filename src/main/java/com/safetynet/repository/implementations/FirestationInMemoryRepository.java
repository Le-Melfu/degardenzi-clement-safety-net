package com.safetynet.repository.implementations;

import com.safetynet.config.loader.FakeDatabase;
import com.safetynet.model.FirestationMapping;
import com.safetynet.repository.interfaces.FirestationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FirestationInMemoryRepository implements FirestationRepository {

    @Override
    public FirestationMapping findByAddress(String address) {
        List<FirestationMapping> firestationMappingArrayList = FakeDatabase.getFirestationMappings();

        return firestationMappingArrayList.stream()
                .filter(f -> f.getAddress().equalsIgnoreCase(address))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<String> getStationAdresses(String station) {
        List<FirestationMapping> firestationMappingArrayList = FakeDatabase.getFirestationMappings();
        return firestationMappingArrayList.stream()
                .filter(f -> f.getStation().equals(station))
                .map(FirestationMapping::getAddress).toList();
    }

    @Override
    public void createNewFirestationMapping(FirestationMapping firestationMapping) {
        List<FirestationMapping> firestationMappingArrayList = FakeDatabase.getFirestationMappings();
        boolean exists = firestationMappingArrayList.stream()
                .anyMatch(f -> f.getAddress().equalsIgnoreCase(firestationMapping.getAddress()));
        if (!exists) {
            firestationMappingArrayList.add(firestationMapping);
        }
    }

    @Override
    public void updateFirestationMapping(FirestationMapping firestationMapping) {
        List<FirestationMapping> firestationMappingArrayList = FakeDatabase.getFirestationMappings();
        for (int i = 0; i < firestationMappingArrayList.size(); i++) {
            FirestationMapping existing = firestationMappingArrayList.get(i);
            if (existing.getAddress().equalsIgnoreCase(firestationMapping.getAddress())) {
                firestationMappingArrayList.set(i, firestationMapping);
                return;
            }
        }
    }

    @Override
    public void deleteFirestationMappingByAddress(String address) {
        List<FirestationMapping> firestationMappingArrayList = FakeDatabase.getFirestationMappings();
        firestationMappingArrayList.removeIf(f -> f.getAddress().equalsIgnoreCase(address));
    }
}
