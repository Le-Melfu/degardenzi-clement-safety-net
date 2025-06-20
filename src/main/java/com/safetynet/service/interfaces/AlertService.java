package com.safetynet.service;

import java.util.List;
import java.util.Map;

public interface AlertService {

    Object getPersonsCoveredByStation(String stationNumber);

    Object getChildrenByAddress(String address);

    List<String> getPhoneNumbersByStation(String stationNumber);

    Object getHouseholdAndMedicalInfo(String address);

    Map<String, List<Object>> getHouseholdsByStations(List<String> stationNumbers);

    Object getDetailedPersonInfo(String firstName, String lastName);

    List<String> getEmailsByCity(String city);

    int calculateAge(String birthdate);
}
