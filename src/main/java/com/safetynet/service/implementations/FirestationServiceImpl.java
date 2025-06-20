package com.safetynet.service.implementations;

import com.safetynet.model.Firestation;
import com.safetynet.repository.interfaces.FirestationRepository;
import com.safetynet.service.interfaces.FirestationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationServiceImpl implements FirestationService {

    private final FirestationRepository firestationRepository;

    public FirestationServiceImpl(FirestationRepository firestationRepository) {
        this.firestationRepository = firestationRepository;
    }

    public List<Firestation> getAllFirestations() {
        return firestationRepository.findAll();
    }

    public Firestation getFirestationByAddress(String address) {
        return firestationRepository.findByAddress(address);
    }

    public List<String> getAddressesByStation(String stationNumber) {
        return firestationRepository.getAddressesByStation(stationNumber);
    }

    public boolean createNewFirestation(Firestation firestation) {
        if (firestationRepository.findByAddress(firestation.getAddress()) == null) {
            firestationRepository.createNewFirestation(firestation);
            return true;
        }
        return false;
    }

    public boolean updateFirestation(Firestation firestation) {
        if (firestationRepository.findByAddress(firestation.getAddress()) != null) {
            firestationRepository.updateFirestation(firestation);
            return true;
        }
        return false;
    }

    public boolean deleteFirestationByAddress(String address) {
        if (firestationRepository.findByAddress(address) != null) {
            firestationRepository.deleteFirestationByAddress(address);
            return true;
        }
        return false;
    }
}
