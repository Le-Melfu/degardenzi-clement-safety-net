package com.safetynet.repository.implementations;

import com.safetynet.config.loader.FakeDatabase;
import com.safetynet.model.Firestation;
import com.safetynet.repository.interfaces.FirestationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FirestationInMemoryRepository implements FirestationRepository {
    
    @Override
    public List<Firestation> findAll() {
        return FakeDatabase.getFirestations();
    }

    @Override
    public Firestation findByAddress(String address) {
        List<Firestation> firestationArrayList = FakeDatabase.getFirestations();

        return firestationArrayList.stream()
                .filter(f -> f.getAddress().equalsIgnoreCase(address))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<String> getStationAdresses(String station) {
        List<Firestation> firestationArrayList = FakeDatabase.getFirestations();
        return firestationArrayList.stream()
                .filter(f -> f.getStation().equals(station))
                .map(Firestation::getAddress).toList();
    }

    @Override
    public void createNewFirestationMapping(Firestation firestation) {
        List<Firestation> firestationArrayList = FakeDatabase.getFirestations();
        boolean exists = firestationArrayList.stream()
                .anyMatch(f -> f.getAddress().equalsIgnoreCase(firestation.getAddress()));
        if (!exists) {
            firestationArrayList.add(firestation);
        }
    }

    @Override
    public void updateFirestationMapping(Firestation firestation) {
        List<Firestation> firestationArrayList = FakeDatabase.getFirestations();
        for (int i = 0; i < firestationArrayList.size(); i++) {
            Firestation existing = firestationArrayList.get(i);
            if (existing.getAddress().equalsIgnoreCase(firestation.getAddress())) {
                firestationArrayList.set(i, firestation);
                return;
            }
        }
    }

    @Override
    public void deleteFirestationMappingByAddress(String address) {
        List<Firestation> firestationArrayList = FakeDatabase.getFirestations();
        firestationArrayList.removeIf(f -> f.getAddress().equalsIgnoreCase(address));
    }
}
