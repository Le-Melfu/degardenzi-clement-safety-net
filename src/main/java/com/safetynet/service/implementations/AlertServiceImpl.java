package com.safetynet.service.implementations;

import com.safetynet.model.Person;
import com.safetynet.model.dto.ChildAlertDTO;
import com.safetynet.model.dto.ChildDTO;
import com.safetynet.model.dto.PersonPublicInfosDTO;
import com.safetynet.service.interfaces.AlertService;
import com.safetynet.service.interfaces.FirestationService;
import com.safetynet.service.interfaces.MedicalRecordService;
import com.safetynet.service.interfaces.PersonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
