package com.safetynet.service.interfaces;

import java.util.List;
import java.util.Map;

public interface AlertService {

    Object getChildrenByAddress(String address);

    List<String> getPhoneNumbersByStation(String stationNumber);

    Object getHouseholdAndMedicalInfo(String address);

    Map<String, List<Object>> getHouseholdsByStations(List<String> stationNumbers);

    Object getDetailedPersonInfo(String firstName, String lastName);

    List<String> getEmailsByCity(String city);

}
