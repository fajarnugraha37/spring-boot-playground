package com.jhipster.service;

import com.jhipster.service.dto.TaskDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.jhipster.domain.Task}.
 */
public interface TaskService {
    /**
     * Save a task.
     *
     * @param taskDTO the entity to save.
     * @return the persisted entity.
     */
    TaskDTO save(TaskDTO taskDTO);

    /**
     * Updates a task.
     *
     * @param taskDTO the entity to update.
     * @return the persisted entity.
     */
    TaskDTO update(TaskDTO taskDTO);

    /**
     * Partially updates a task.
     *
     * @param taskDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TaskDTO> partialUpdate(TaskDTO taskDTO);

    /**
     * Get all the tasks.
     *
     * @return the list of entities.
     */
    List<TaskDTO> findAll();

    /**
     * Get the "id" task.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaskDTO> findOne(Long id);

    /**
     * Delete the "id" task.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the task corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    List<TaskDTO> search(String query);
}
