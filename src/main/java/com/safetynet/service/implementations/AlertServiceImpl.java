package com.safetynet.service.implementations;

import com.safetynet.model.FirestationMapping;
import com.safetynet.model.MedicalRecord;
import com.safetynet.model.Person;
import com.safetynet.model.dto.*;
import com.safetynet.service.interfaces.AlertService;
import com.safetynet.service.interfaces.FirestationService;
import com.safetynet.service.interfaces.MedicalRecordService;
import com.safetynet.service.interfaces.PersonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    public ChildAlertDTO getChildrenByAddress(String address) {
        List<Person> residents = personService.getPersonsByAddress(address);

        List<ChildDTO> children = new ArrayList<>();
        List<PersonPublicInfosDTO> otherMembers = new ArrayList<>();

        for (Person p : residents) {
            try {
                String birthdate = medicalRecordService.getBirthdate(p.getFirstName(), p.getLastName());
                int age = medicalRecordService.calculateAge(birthdate);

                if (age <= 18) {
                    children.add(new ChildDTO(p.getFirstName(), p.getLastName(), age));
                } else {
                    otherMembers.add(new PersonPublicInfosDTO(p.getFirstName(), p.getLastName(), p.getAddress(), p.getPhone()));
                }

            } catch (IllegalArgumentException e) {
                // TODO: add log
            }
        }

        return new ChildAlertDTO(children, otherMembers);
    }

    @Override
    public List<String> getPhoneNumbersByStation(String stationNumber) {
        List<String> addresses = firestationService.getStationAdresses(stationNumber);
        return addresses.stream()
                .flatMap(address -> personService.getPersonsByAddress(address).stream())
                .map(Person::getPhone)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }

    @Override
    public FireIncidentDTO getFireIncidentByAddress(String address) {
        FirestationMapping station = firestationService.getFirestationByAddress(address);
        if (station == null) {
            return null;
        }
        String stationNumber = station.getStation();

        List<Person> residents = personService.getPersonsByAddress(address);

        List<PersonWithMedicalDataDTO> personsMedicalInfos = residents.stream().map(person -> {
            MedicalRecord record = medicalRecordService.getMedicalRecordByFullName(
                    person.getFirstName(), person.getLastName());


            int age = medicalRecordService.calculateAge(record != null ? record.getBirthdate() : null);

            return new PersonWithMedicalDataDTO(
                    person.getFirstName(),
                    person.getLastName(),
                    person.getPhone(),
                    age,
                    record != null ? record.getMedications() : Collections.emptyList(),
                    record != null ? record.getAllergies() : Collections.emptyList()
            );
        }).toList();

        return new FireIncidentDTO(stationNumber, personsMedicalInfos);
    }


    @Override
    public List<StationFloodCoverageDTO> getStationsFloodCoverage(List<String> stationNumbers) {
        List<StationFloodCoverageDTO> result = new ArrayList<>();

        for (String stationNumber : stationNumbers) {
            // Get adresses served by this station
            List<String> addresses = firestationService.getStationAdresses(stationNumber);
            List<HouseholdWithMedicalDataDTO> households = new ArrayList<>();

            for (String address : addresses) {
                List<Person> residents = personService.getPersonsByAddress(address);

                // Get medical datas for each resident
                List<PersonWithMedicalDataDTO> residentsDTO = residents.stream()
                        .map(person -> {
                            MedicalRecord record = medicalRecordService.getMedicalRecordByFullName(person.getFirstName(), person.getLastName());
                            int age = medicalRecordService.calculateAge(record != null ? record.getBirthdate() : null);
                            return new PersonWithMedicalDataDTO(
                                    person.getFirstName(),
                                    person.getLastName(),
                                    person.getPhone(),
                                    age,
                                    record != null ? record.getMedications() : Collections.emptyList(),
                                    record != null ? record.getAllergies() : Collections.emptyList()
                            );
                        })
                        .toList();

                households.add(new HouseholdWithMedicalDataDTO(address, residentsDTO));
            }

            result.add(new StationFloodCoverageDTO(stationNumber, households));
        }

        return result;
    }


    @Override
    public List<PersonWithMedicalDataDTO> getPersonsInfosByLastName(String lastName) {
        List<Person> allPersons = personService.getAllPersons();

        return allPersons.stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
                .map(p -> {
                    MedicalRecord record = medicalRecordService.getMedicalRecordByFullName(p.getFirstName(), p.getLastName());
                    int age = medicalRecordService.calculateAge(record != null ? record.getBirthdate() : null);
                    return new PersonWithMedicalDataDTO(
                            p.getFirstName(),
                            p.getLastName(),
                            p.getPhone(),
                            age,
                            record != null ? record.getMedications() : Collections.emptyList(),
                            record != null ? record.getAllergies() : Collections.emptyList()
                    );
                })
                .toList();
    }

    @Override
    public List<String> getEmailsByCity(String city) {
        return personService.getAllPersons().stream()
                .filter(p -> p.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .distinct()
                .toList();
    }
}
