package com.safetynet.service.interfaces;

import com.safetynet.model.dto.ChildAlertDTO;
import com.safetynet.model.dto.FireIncidentDTO;
import com.safetynet.model.dto.PersonWithMedicalDataDTO;
import com.safetynet.model.dto.StationFloodCoverageDTO;

import java.util.List;

/**
 * AlertService defines various alert-related services
 * for accessing child data, emergency contact info,
 * fire incidents, flood coverage, and medical data of individuals.
 */
public interface AlertService {

    /**
     * Retrieves a list of children (age ≤ 18) living at the specified address,
     * along with a list of other household members.
     *
     * @param address the address to search for children and other household members
     * @return a {@link ChildAlertDTO} containing the children and household members
     */
    ChildAlertDTO getChildrenByAddress(String address);

    /**
     * Retrieves the list of phone numbers for all residents
     * served by the specified fire station.
     *
     * @param stationNumber the fire station number
     * @return a list of phone numbers for residents covered by the station
     */
    List<String> getPhoneNumbersByStation(String stationNumber);

    /**
     * Retrieves all persons living at the specified address
     * with their medical records and the station number responsible.
     *
     * @param address the address to search
     * @return a {@link FireIncidentDTO} containing personal and station information
     */
    FireIncidentDTO getFireIncidentByAddress(String address);

    /**
     * Retrieves a list of households covered by each station number
     * in case of flooding, including medical information for each person.
     *
     * @param stationNumbers the list of station numbers
     * @return a list of {@link StationFloodCoverageDTO}, one per station number
     */
    List<StationFloodCoverageDTO> getStationsFloodCoverage(List<String> stationNumbers);

    /**
     * Retrieves personal and medical information for all individuals
     * with the given last name.
     *
     * @param lastName the last name to search
     * @return a list of {@link PersonWithMedicalDataDTO} for matching individuals
     */
    List<PersonWithMedicalDataDTO> getPersonsInfosByLastName(String lastName);

    /**
     * Retrieves the email addresses of all residents in the specified city.
     *
     * @param city the city to search
     * @return a list of email addresses for residents of the city
     */
    List<String> getEmailsByCity(String city);
}
