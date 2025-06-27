package com.safetynet.service.implementations;

import com.safetynet.service.interfaces.AlertService;
import com.safetynet.service.interfaces.FirestationService;
import com.safetynet.service.interfaces.MedicalRecordService;
import com.safetynet.service.interfaces.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AlertServiceImpl implements AlertService {

    private final PersonService personService;
    private final MedicalRecordService medicalRecordService;
    private final FirestationService firestationService;

    public AlertServiceImpl(
            PersonService personService,
            MedicalRecordService medicalRecordService,
            FirestationService firestationService
    ) {
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
        this.firestationService = firestationService;
    }


    @Override
    public Object getChildrenByAddress(String address) {
        // TODO: logic for /childAlert
        return null;
    }

    @Override
    public List<String> getPhoneNumbersByStation(String stationNumber) {
        // TODO: logic for /phoneAlert
        return List.of();
    }

    @Override
    public Object getHouseholdAndMedicalInfo(String address) {
        // TODO: logic for /fire
        return null;
    }

    @Override
    public Map<String, List<Object>> getHouseholdsByStations(List<String> stationNumbers) {
        // TODO: logic for /flood/stations
        return Map.of();
    }

    @Override
    public Object getDetailedPersonInfo(String firstName, String lastName) {
        // TODO: logic for /personInfo
        return null;
    }

    @Override
    public List<String> getEmailsByCity(String city) {
        // TODO: logic for /communityEmail
        return List.of();
    }
}
