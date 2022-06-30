package ma.itroad.ram.kpi.service;

import java.util.List;
import java.util.Optional;

import ma.itroad.ram.kpi.domain.KpiGroup;
import ma.itroad.ram.kpi.service.dto.AssignmentWrapperDTO;
import ma.itroad.ram.kpi.service.dto.KpiGroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.itroad.ram.kpi.domain.KpiGroup}.
 */
public interface KpiGroupService {
    /**
     * Save a kpiGroup.
     *
     * @param kpiGroupDTO the entity to save.
     * @return the persisted entity.
     */
    KpiGroupDTO save(KpiGroupDTO kpiGroupDTO);

    /**
     * Partially updates a kpiGroup.
     *
     * @param kpiGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KpiGroupDTO> partialUpdate(KpiGroupDTO kpiGroupDTO);

    /**
     * Get all the kpiGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KpiGroupDTO> findAll(String label, Pageable pageable);


    /**
     * Get the "id" kpiGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KpiGroupDTO> findOne(Long id);

    /**
     * Delete the "id" kpiGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Page<KpiGroupDTO> findAllJoinAssignments(Pageable pageable);


    List<AssignmentWrapperDTO> addUsersDetails(Page<KpiGroupDTO> page, String token);

}
