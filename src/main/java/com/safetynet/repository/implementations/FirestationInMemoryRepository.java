package com.safetynet.repository.implementations;

import com.safetynet.model.Firestation;
import com.safetynet.repository.interfaces.FirestationRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FirestationInMemoryRepository implements FirestationRepository {

    private final List<Firestation> firestationArrayList = new ArrayList<>();

    public void setData(List<Firestation> data) {
        firestationArrayList.clear();
        firestationArrayList.addAll(data);
    }

    @Override
    public List<Firestation> findAll() {
        return List.copyOf(firestationArrayList);
    }

    @Override
    public Firestation findByAddress(String address) {
        return firestationArrayList.stream()
                .filter(f -> f.getAddress().equalsIgnoreCase(address))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<String> getAddressesByStation(String station) {
        return firestationArrayList.stream()
                .filter(f -> f.getStation().equals(station))
                .map(Firestation::getAddress)
                .toList();
    }

    @Override
    public void createNewFirestation(Firestation firestation) {
        boolean exists = firestationArrayList.stream()
                .anyMatch(f -> f.getAddress().equalsIgnoreCase(firestation.getAddress()));
        if (!exists) {
            firestationArrayList.add(firestation);
        }
    }

    @Override
    public void updateFirestation(Firestation firestation) {
        for (int i = 0; i < firestationArrayList.size(); i++) {
            Firestation existing = firestationArrayList.get(i);
            if (existing.getAddress().equalsIgnoreCase(firestation.getAddress())) {
                firestationArrayList.set(i, firestation);
                return;
            }
        }
    }

    @Override
    public void deleteFirestationByAddress(String address) {
        firestationArrayList.removeIf(f -> f.getAddress().equalsIgnoreCase(address));
    }
}
