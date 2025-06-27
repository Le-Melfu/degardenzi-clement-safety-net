package com.safetynet.service.interfaces;

import com.safetynet.model.FirestationMapping;
import com.safetynet.model.dto.FirestationCoverageDTO;

import java.util.List;

public interface FirestationService {
    FirestationMapping getFirestationByAddress(String address);

    List<String> getStationAdresses(String stationNumber);

    FirestationCoverageDTO getPersonsCoveredByStation(String stationNumber);

    boolean createNewFirestation(FirestationMapping firestationMapping);

    boolean updateFirestation(FirestationMapping firestationMapping);

    boolean deleteFirestationByAddress(String address);
}
