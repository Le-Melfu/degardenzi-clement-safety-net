package com.safetynet.service.implementations;

import com.safetynet.model.FirestationMapping;
import com.safetynet.model.Person;
import com.safetynet.model.dto.FirestationCoverageDTO;
import com.safetynet.model.dto.PersonPublicInfosDTO;
import com.safetynet.repository.interfaces.FirestationRepository;
import com.safetynet.service.interfaces.FirestationService;
import com.safetynet.service.interfaces.MedicalRecordService;
import com.safetynet.service.interfaces.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationServiceImpl implements FirestationService {

    private final FirestationRepository firestationRepository;
    private final PersonService personService;
    private final MedicalRecordService medicalRecordService;

    public FirestationServiceImpl(FirestationRepository firestationRepository, PersonService personService, MedicalRecordService medicalRecordService) {
        this.firestationRepository = firestationRepository;
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
    }


    @Override
    public FirestationMapping getFirestationByAddress(String address) {
        return firestationRepository.findByAddress(address);
    }

    @Override
    public List<String> getStationAdresses(String stationNumber) {
        return firestationRepository.getStationAdresses(stationNumber);
    }

    @Override
    public FirestationCoverageDTO getPersonsCoveredByStation(String stationNumber) {
        List<String> stationAddresses = getStationAdresses(stationNumber);
        if (stationAddresses.isEmpty()) {
            return null;
        }
        List<Person> persons = stationAddresses.stream()
                .flatMap(address -> personService.getPersonsByAddress(address).stream())
                .toList();

        List<PersonPublicInfosDTO> personDtos = persons.stream()
                .map(p -> new PersonPublicInfosDTO(
                        p.getFirstName(),
                        p.getLastName(),
                        p.getAddress(),
                        p.getPhone()
                ))
                .toList();

        int childCount = (int) persons.stream()
                .filter(p -> {
                    String birthdate = medicalRecordService.getBirthdate(p.getFirstName(), p.getLastName());
                    return medicalRecordService.calculateAge(birthdate, p.getFirstName(), p.getLastName()) <= 18;
                })
                .count();

        int adultCount = personDtos.size() - childCount;

        return new FirestationCoverageDTO(personDtos, adultCount, childCount);
    }

    @Override
    public boolean createNewFirestation(FirestationMapping firestationMapping) {
        boolean alreadyExists = firestationRepository.findAll().stream()
                .anyMatch(fs -> fs.getAddress().equalsIgnoreCase(firestationMapping.getAddress())
                        && fs.getStation().equalsIgnoreCase(firestationMapping.getStation()));

        if (!alreadyExists) {
            firestationRepository.createNewFirestationMapping(firestationMapping);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateFirestation(FirestationMapping firestationMapping) {
        if (firestationRepository.findByAddress(firestationMapping.getAddress()) != null) {
            firestationRepository.updateFirestationMapping(firestationMapping);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteFirestationByAddress(String address) {
        if (firestationRepository.findByAddress(address) != null) {
            firestationRepository.deleteFirestationMappingByAddress(address);
            return true;
        }
        return false;
    }
}
