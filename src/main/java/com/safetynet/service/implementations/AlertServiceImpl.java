package com.safetynet.service.implementations;

import com.safetynet.model.Firestation;
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
        String address = firestationService.getStationAdress(stationNumber);
        List<Person> persons = personService.getPersonsByAddress(address);

        return persons.stream()
                .map(Person::getPhone)
                .distinct()
                .toList();
    }

    @Override
    public FireIncidentDTO getFireIncidentByAddress(String address) {
        Firestation station = firestationService.getFirestationByAddress(address);
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

        // 4. Retourner le DTO
        return new FireIncidentDTO(stationNumber, personsMedicalInfos);
    }


    @Override
    public List<HouseholdWithMedicalDataDTO> getHouseholdWithMedicalByStations(List<String> stationNumber) {
        /*
         * pour chaque station passée en paramètre,
         * retourner la liste des habitants desservis par cette station
         * regroupés par adresse
         * avec leurs infos médicales
         * */
    }


    @Override
    public Object getDetailedPersonInfo(String firstName, String lastName) {
        // TODO
        return null;
    }

    @Override
    public List<String> getEmailsByCity(String city) {
        // TODO
        return List.of();
    }
}
