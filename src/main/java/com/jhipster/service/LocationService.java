package com.jhipster.service;

import com.jhipster.service.dto.LocationDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.jhipster.domain.Location}.
 */
public interface LocationService {
    /**
     * Save a location.
     *
     * @param locationDTO the entity to save.
     * @return the persisted entity.
     */
    LocationDTO save(LocationDTO locationDTO);

    /**
     * Updates a location.
     *
     * @param locationDTO the entity to update.
     * @return the persisted entity.
     */
    LocationDTO update(LocationDTO locationDTO);

    /**
     * Partially updates a location.
     *
     * @param locationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LocationDTO> partialUpdate(LocationDTO locationDTO);

    /**
     * Get all the locations.
     *
     * @return the list of entities.
     */
    List<LocationDTO> findAll();

    /**
     * Get the "id" location.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LocationDTO> findOne(Long id);

    /**
     * Delete the "id" location.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the location corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    List<LocationDTO> search(String query);
}
