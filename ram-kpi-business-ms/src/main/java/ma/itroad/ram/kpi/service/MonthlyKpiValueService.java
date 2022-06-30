package ma.itroad.ram.kpi.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ma.itroad.ram.kpi.domain.Kpi;
import ma.itroad.ram.kpi.domain.MonthlyKpiValue;
import ma.itroad.ram.kpi.domain.enumeration.KpiValueType;
import ma.itroad.ram.kpi.domain.enumeration.ValRef;
import ma.itroad.ram.kpi.service.dto.KpiDTO;
import ma.itroad.ram.kpi.service.dto.MonthlyKpiValueDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Interface for managing {@link ma.itroad.ram.kpi.domain.MonthlyKpiValue}.
 */
public interface MonthlyKpiValueService {
    /**
     * Save a monthlyKpiValue.
     *
     * @param monthlyKpiValueDTO the entity to save.
     * @return the persisted entity.
     */
    MonthlyKpiValueDTO save(MonthlyKpiValueDTO monthlyKpiValueDTO);

    /**
     * Partially updates a monthlyKpiValue.
     *
     * @param monthlyKpiValueDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MonthlyKpiValueDTO> partialUpdate(MonthlyKpiValueDTO monthlyKpiValueDTO);

    /**
     * Get all the monthlyKpiValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MonthlyKpiValueDTO> findAll(Pageable pageable);

    /**
     * Get the "id" monthlyKpiValue.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MonthlyKpiValueDTO> findOne(Long id);

    /**
     * Delete the "id" monthlyKpiValue.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<MonthlyKpiValueDTO> importMonthlyKpiValues(MultipartFile file, KpiValueType kpiValType);

    Optional<MonthlyKpiValueDTO> findByKpiAndDate(Kpi kpi, LocalDate ind2ValRefDate);

    Double getValueByKpiAndDate(Kpi kpi, LocalDate date);

    Double sumValuesByKpiAndDatesBetweenStartAndEnd(Kpi kpi,LocalDate start, LocalDate end);

    Double getValueByKpiAndDateAndBudgetVersionType(Kpi toEntity, LocalDate previousDate);
}
