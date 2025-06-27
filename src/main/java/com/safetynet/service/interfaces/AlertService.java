package com.safetynet.service.interfaces;

import com.safetynet.model.dto.ChildAlertDTO;
import com.safetynet.model.dto.FireIncidentDTO;
import com.safetynet.model.dto.HouseholdWithMedicalDataDTO;

import java.util.List;

public interface AlertService {

    ChildAlertDTO getChildrenByAddress(String address);

    List<String> getPhoneNumbersByStation(String stationNumber);

    public FireIncidentDTO getFireIncidentByAddress(String address);

    public List<HouseholdWithMedicalDataDTO> getHouseholdWithMedicalByStation(String stationNumbers);

    Object getDetailedPersonInfo(String firstName, String lastName);

    List<String> getEmailsByCity(String city);

}
