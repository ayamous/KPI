package ma.itroad.ram.kpi.service;

import java.util.Optional;

import ma.itroad.ram.kpi.service.dto.SystemSourceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.itroad.ram.kpi.domain.SystemSource}.
 */
public interface SystemSourceService {
    /**
     * Save a systemSource.
     *
     * @param systemSourceDTO the entity to save.
     * @return the persisted entity.
     */
    SystemSourceDTO save(SystemSourceDTO systemSourceDTO);

    /**
     * Partially updates a systemSource.
     *
     * @param systemSourceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SystemSourceDTO> partialUpdate(SystemSourceDTO systemSourceDTO);

    /**
     * Get all the systemSources.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SystemSourceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" systemSource.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SystemSourceDTO> findOne(Long id);

    /**
     * Delete the "id" systemSource.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
