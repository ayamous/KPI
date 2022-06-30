package ma.itroad.ram.kpi.service;

import java.util.List;
import java.util.Optional;

import ma.itroad.ram.kpi.domain.Kpi;
import ma.itroad.ram.kpi.service.dto.ControlRuleDTO;
import ma.itroad.ram.kpi.service.dto.KpiDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Interface for managing {@link ma.itroad.ram.kpi.domain.ControlRule}.
 */
public interface ControlRuleService {
    /**
     * Save a controlRule.
     *
     * @param controlRuleDTO the entity to save.
     * @return the persisted entity.
     */
    ControlRuleDTO save(ControlRuleDTO controlRuleDTO);

    /**
     * Partially updates a controlRule.
     *
     * @param controlRuleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ControlRuleDTO> partialUpdate(ControlRuleDTO controlRuleDTO);

    /**
     * Get all the controlRules.
     *
     * @return the list of entities.
     */
    Page<ControlRuleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" controlRule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ControlRuleDTO> findOne(Long id);

    /**
     * Delete the "id" controlRule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<ControlRuleDTO> findByKpiBase(Kpi kpi);

}
