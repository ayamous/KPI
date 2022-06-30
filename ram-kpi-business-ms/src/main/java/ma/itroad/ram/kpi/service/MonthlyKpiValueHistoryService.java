package ma.itroad.ram.kpi.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import ma.itroad.ram.kpi.service.dto.MonthlyKpiValueHistoryDTO;

/**
 * Service Interface for managing {@link ma.itroad.ram.kpi.domain.MonthlyKpiValueHistory}.
 */
public interface MonthlyKpiValueHistoryService {
    /**
     * Save a monthlyKpiValueHistory.
     *
     * @param monthlyKpiValueHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    MonthlyKpiValueHistoryDTO save(MonthlyKpiValueHistoryDTO monthlyKpiValueHistoryDTO);

    /**
     * Partially updates a monthlyKpiValueHistory.
     *
     * @param monthlyKpiValueHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MonthlyKpiValueHistoryDTO> partialUpdate(MonthlyKpiValueHistoryDTO monthlyKpiValueHistoryDTO);

    /**
     * Get all the monthlyKpiValueHistories.
     *
     * @return the list of entities.
     */
    List<MonthlyKpiValueHistoryDTO> findAll();

    /**
     * Get the "id" monthlyKpiValueHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MonthlyKpiValueHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" monthlyKpiValueHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);


    /**
     * Get all the monthlyKpiValueHistories by monthlyKpiValue id.
     *
     * @return the list of entities.
     */
    List<MonthlyKpiValueHistoryDTO> findByMonthlyKpiValueId(Long id);
}

