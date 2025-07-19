package com.safetynet.service.interfaces;

import com.safetynet.model.FirestationMapping;
import com.safetynet.model.dto.FirestationCoverageDTO;

import java.util.List;

/**
 * FirestationService provides operations for managing fire station mappings
 * and retrieving data related to fire station coverage.
 */
public interface FirestationService {

    /**
     * Retrieves the fire station mapping (station number and address)
     * for the specified address.
     *
     * @param address the address to look up
     * @return a {@link FirestationMapping} for the address, or null if not found
     */
    FirestationMapping getFirestationByAddress(String address);

    /**
     * Retrieves a list of addresses covered by a specific fire station.
     *
     * @param stationNumber the fire station number
     * @return a list of addresses served by the fire station
     */
    List<String> getStationAdresses(String stationNumber);

    /**
     * Retrieves a list of persons covered by the given fire station,
     * along with statistics such as the number of adults and children.
     *
     * @param stationNumber the fire station number
     * @return a {@link FirestationCoverageDTO} with persons and coverage stats
     */
    FirestationCoverageDTO getPersonsCoveredByStation(String stationNumber);

    /**
     * Creates a new fire station mapping (station number + address).
     *
     * @param firestationMapping the fire station mapping to create
     * @return true if the mapping was created successfully, false otherwise
     */
    boolean createNewFirestation(FirestationMapping firestationMapping);

    /**
     * Updates an existing fire station mapping.
     *
     * @param firestationMapping the updated fire station mapping
     * @return true if the mapping was updated successfully, false otherwise
     */
    boolean updateFirestation(FirestationMapping firestationMapping);

    /**
     * Deletes the fire station mapping corresponding to the given address.
     *
     * @param address the address to remove from the fire station mapping
     * @return true if the mapping was deleted successfully, false otherwise
     */
    boolean deleteFirestationByAddress(String address);
}
