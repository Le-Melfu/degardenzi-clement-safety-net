package com.safetynet.repository.interfaces;


import com.safetynet.model.Firestation;

import java.util.List;

public interface FirestationRepository {

    /**
     * Retrieves all firestation-address mappings.
     *
     * @return a list of all Firestation mappings
     */
    List<Firestation> findAll();

    /**
     * Finds a firestation mapping by address.
     *
     * @param address the address to search for
     * @return the corresponding Firestation mapping, or null if not found
     */
    Firestation findByAddress(String address);

    /**
     * Retrieves a list of addresses covered by a given firestation number.
     *
     * @param station the firestation number
     * @return a list of addresses associated with the given station
     */
    List<String> getStationAdresses(String station);

    /**
     * Adds a new firestation-address mapping to the repository.
     *
     * @param firestation the firestation mapping to create
     */
    void createNewFirestationMapping(Firestation firestation);

    /**
     * Updates an existing firestation-address mapping.
     * The mapping is identified by the address field.
     *
     * @param firestation the updated firestation mapping
     */
    void updateFirestationMapping(Firestation firestation);

    /**
     * Deletes a firestation-address mapping by address.
     *
     * @param address the address of the mapping to delete
     */
    void deleteFirestationMappingByAddress(String address);
}
