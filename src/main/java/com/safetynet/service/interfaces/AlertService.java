package com.safetynet.service.interfaces;

import com.safetynet.model.dto.ChildAlertDTO;
import com.safetynet.model.dto.FireIncidentDTO;
import com.safetynet.model.dto.PersonWithMedicalDataDTO;
import com.safetynet.model.dto.StationFloodCoverageDTO;

import java.util.List;

public interface AlertService {

    ChildAlertDTO getChildrenByAddress(String address);

    List<String> getPhoneNumbersByStation(String stationNumber);

    FireIncidentDTO getFireIncidentByAddress(String address);

    List<StationFloodCoverageDTO> getStationsFloodCoverage(List<String> stationNumbers);

    List<PersonWithMedicalDataDTO> getPersonsInfosByLastName(String lastName);

    List<String> getEmailsByCity(String city);

}
