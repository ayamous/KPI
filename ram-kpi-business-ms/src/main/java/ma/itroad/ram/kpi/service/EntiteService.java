package ma.itroad.ram.kpi.service;

import java.util.Optional;

import ma.itroad.ram.kpi.service.dto.EntiteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.itroad.ram.kpi.domain.Entite}.
 */
public interface EntiteService {
    /**
     * Save a entite.
     *
     * @param entiteDTO the entity to save.
     * @return the persisted entity.
     */
    EntiteDTO save(EntiteDTO entiteDTO);

    /**
     * Partially updates a entite.
     *
     * @param entiteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EntiteDTO> partialUpdate(EntiteDTO entiteDTO);

    /**
     * Get all the entites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EntiteDTO> findAll(Pageable pageable);

    /**
     * Get the "id" entite.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EntiteDTO> findOne(Long id);

    /**
     * Delete the "id" entite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
