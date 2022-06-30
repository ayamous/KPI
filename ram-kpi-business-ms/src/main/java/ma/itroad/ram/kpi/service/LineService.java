package ma.itroad.ram.kpi.service;

import java.util.List;
import java.util.Optional;

import ma.itroad.ram.kpi.service.dto.LineDTO;

/**
 * Service Interface for managing {@link ma.itroad.ram.kpi.domain.Line}.
 */
public interface LineService {
    /**
     * Save a line.
     *
     * @param lineDTO the entity to save.
     * @return the persisted entity.
     */
    LineDTO save(LineDTO lineDTO);

    /**
     * Partially updates a line.
     *
     * @param lineDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LineDTO> partialUpdate(LineDTO lineDTO);

    /**
     * Get all the lines.
     *
     * @return the list of entities.
     */
    List<LineDTO> findAll();

    /**
     * Get the "id" line.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LineDTO> findOne(Long id);

    /**
     * Delete the "id" line.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
