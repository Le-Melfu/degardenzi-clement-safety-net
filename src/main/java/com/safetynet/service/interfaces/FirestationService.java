package com.safetynet.service.interfaces;

import com.safetynet.model.Firestation;

import java.util.List;

public interface FirestationService {
    List<Firestation> getAllFirestations();

    Firestation getFirestationByAddress(String address);

    List<String> getAddressesByStation(String stationNumber);

    boolean createNewFirestation(Firestation firestation);

    boolean updateFirestation(Firestation firestation);

    boolean deleteFirestationByAddress(String address);
}
