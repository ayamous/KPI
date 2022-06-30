package ma.itroad.ram.kpi.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ma.itroad.ram.kpi.domain.enumeration.SortingOrder;
import ma.itroad.ram.kpi.domain.enumeration.Status;
import ma.itroad.ram.kpi.service.dto.KpiDTO;
import ma.itroad.ram.kpi.service.dto.KpiDetailDTO;
import ma.itroad.ram.kpi.web.rest.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Interface for managing {@link ma.itroad.ram.kpi.domain.Kpi}.
 */
public interface KpiService {
    /**
     * Save a kpi.
     *
     * @param kpiDTO the entity to save.
     * @return the persisted entity.
     */

    KpiDTO save(KpiDTO kpiDTO);

    /**
     * Partially updates a kpi.
     *
     * @param kpiDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KpiDTO> partialUpdate(KpiDTO kpiDTO);

    /**
     * Get all the kpis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KpiDTO> findAll(String filterBy, Pageable pageable);

    /**
     * Get the "id" kpi.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KpiDTO> findOne(Long id);

    /**
     * Delete the "id" kpi.
     *
     * @param id
     */
    void delete(Long id);

    /**
     * findByIdAndkpiGroupId the "kpiid" kpi.
     *
     * @param kpiGroupId
     */
    Optional<KpiDTO> findByIdAndkpiGroupId(Long kpiId, Long kpiGroupId);

    // Page<KpiDTO> KpisFilter(Long entiteId, Long categoryId, Status status, Status reminder, String filterBy, Integer year, Pageable pageable);
    /**
     *
     * @param year
     */
    Page<KpiDTO> findByCriteria(KpiCriteria criteria, Integer year, HttpHeaders responseHeaders , Pageable page) throws ParseException;
    /**
     *
     * @param year
     */
    ByteArrayInputStream load(List<KpiDTO> kpis, Integer year);
    /**
     *
     * @param year
     */
    KpiDetailDTO findDetailOne(Long id,Integer year) throws ResourceNotFoundException;
    /**
     *
     * @param year
     */
    ByteArrayInputStream loadKpiDetailDTO(KpiDetailDTO kpiDetailDTO, Integer year);
}
