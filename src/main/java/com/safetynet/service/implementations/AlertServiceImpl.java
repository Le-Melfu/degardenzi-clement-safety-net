package com.safetynet.service.implementations;

import com.safetynet.model.Person;
import com.safetynet.model.dto.FirestationCoverageDTO;
import com.safetynet.model.dto.PersonPublicInfosDTO;
import com.safetynet.service.AlertService;
import com.safetynet.service.interfaces.FirestationService;
import com.safetynet.service.interfaces.MedicalRecordService;
import com.safetynet.service.interfaces.PersonService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
    public FirestationCoverageDTO getPersonsCoveredByStation(String stationNumber) {
        List<String> addresses = firestationService.getAddressesByStation(stationNumber);

        List<Person> coveredPersons = personService.getAllPersons().stream()
                .filter(p -> addresses.contains(p.getAdress()))
                .toList();

        List<PersonPublicInfosDTO> persons = coveredPersons.stream()
                .map(p -> new PersonPublicInfosDTO(
                        p.getFirstName(),
                        p.getLastName(),
                        p.getAddress(),
                        p.getPhone()
                ))
                .toList();

        int childCount = (int) coveredPersons.stream()
                .filter(p -> {
                    String birthdate = medicalRecordService.getBirthdate(p.getFirstName(), p.getLastName());
                    return calculateAge(birthdate) < 18;
                })
                .count();

        int adultCount = persons.size() - childCount;

        return new FirestationCoverageDTO(persons, adultCount, childCount);
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

    @Override
    public int calculateAge(String birthdate) {
        if (birthdate == null) return 0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birth = LocalDate.parse(birthdate, formatter);
        return Period.between(birth, LocalDate.now()).getYears();
    }
}
