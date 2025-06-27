package com.safetynet.service.interfaces;

import com.safetynet.model.dto.ChildAlertDTO;

import java.util.List;
import java.util.Map;

public interface AlertService {

    ChildAlertDTO getChildrenByAddress(String address);

    List<String> getPhoneNumbersByStation(String stationNumber);

    Object getHouseholdAndMedicalInfo(String address);

    Map<String, List<Object>> getHouseholdsByStations(List<String> stationNumbers);

    Object getDetailedPersonInfo(String firstName, String lastName);

    List<String> getEmailsByCity(String city);

}
