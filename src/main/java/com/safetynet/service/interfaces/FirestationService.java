package com.safetynet.service.interfaces;

import com.safetynet.model.Firestation;
import com.safetynet.model.dto.FirestationCoverageDTO;

import java.util.List;

public interface FirestationService {
    List<Firestation> getAllFirestations();

    Firestation getFirestationByAddress(String address);

    String getStationAdresses(String stationNumber);

    FirestationCoverageDTO getPersonsCoveredByStation(String stationNumber);

    boolean createNewFirestation(Firestation firestation);

    boolean updateFirestation(Firestation firestation);

    boolean deleteFirestationByAddress(String address);
}
