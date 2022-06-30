package ma.itroad.ram.kpi.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ma.itroad.ram.kpi.repository.MonthlyKpiValueHistoryRepository;
import ma.itroad.ram.kpi.service.MonthlyKpiValueHistoryService;
import ma.itroad.ram.kpi.service.dto.MonthlyKpiValueHistoryDTO;
import ma.itroad.ram.kpi.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ma.itroad.ram.kpi.domain.MonthlyKpiValueHistory}.
 */
@RestController
@RequestMapping("/business/api")
public class MonthlyKpiValueHistoryResource {

    private final Logger log = LoggerFactory.getLogger(MonthlyKpiValueHistoryResource.class);

    private static final String ENTITY_NAME = "monthlyKpiValueHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MonthlyKpiValueHistoryService monthlyKpiValueHistoryService;

    private final MonthlyKpiValueHistoryRepository monthlyKpiValueHistoryRepository;

    public MonthlyKpiValueHistoryResource(
            MonthlyKpiValueHistoryService monthlyKpiValueHistoryService,
            MonthlyKpiValueHistoryRepository monthlyKpiValueHistoryRepository
    ) {
        this.monthlyKpiValueHistoryService = monthlyKpiValueHistoryService;
        this.monthlyKpiValueHistoryRepository = monthlyKpiValueHistoryRepository;
    }

    /**
     * {@code POST  /monthly-kpi-value-histories} : Create a new monthlyKpiValueHistory.
     *
     * @param monthlyKpiValueHistoryDTO the monthlyKpiValueHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new monthlyKpiValueHistoryDTO, or with status {@code 400 (Bad Request)} if the monthlyKpiValueHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/monthly-kpi-value-histories")
    public ResponseEntity<MonthlyKpiValueHistoryDTO> createMonthlyKpiValueHistory(
            @RequestBody MonthlyKpiValueHistoryDTO monthlyKpiValueHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to save MonthlyKpiValueHistory : {}", monthlyKpiValueHistoryDTO);
        if (monthlyKpiValueHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new monthlyKpiValueHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MonthlyKpiValueHistoryDTO result = monthlyKpiValueHistoryService.save(monthlyKpiValueHistoryDTO);
        return ResponseEntity
                .created(new URI("/api/monthly-kpi-value-histories/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /monthly-kpi-value-histories/:id} : Updates an existing monthlyKpiValueHistory.
     *
     * @param id                        the id of the monthlyKpiValueHistoryDTO to save.
     * @param monthlyKpiValueHistoryDTO the monthlyKpiValueHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monthlyKpiValueHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the monthlyKpiValueHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the monthlyKpiValueHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/monthly-kpi-value-histories/{id}")
    public ResponseEntity<MonthlyKpiValueHistoryDTO> updateMonthlyKpiValueHistory(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody MonthlyKpiValueHistoryDTO monthlyKpiValueHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MonthlyKpiValueHistory : {}, {}", id, monthlyKpiValueHistoryDTO);
        if (monthlyKpiValueHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, monthlyKpiValueHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!monthlyKpiValueHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MonthlyKpiValueHistoryDTO result = monthlyKpiValueHistoryService.save(monthlyKpiValueHistoryDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, monthlyKpiValueHistoryDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code PATCH  /monthly-kpi-value-histories/:id} : Partial updates given fields of an existing monthlyKpiValueHistory, field will ignore if it is null
     *
     * @param id                        the id of the monthlyKpiValueHistoryDTO to save.
     * @param monthlyKpiValueHistoryDTO the monthlyKpiValueHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monthlyKpiValueHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the monthlyKpiValueHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the monthlyKpiValueHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the monthlyKpiValueHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/monthly-kpi-value-histories/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<MonthlyKpiValueHistoryDTO> partialUpdateMonthlyKpiValueHistory(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody MonthlyKpiValueHistoryDTO monthlyKpiValueHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MonthlyKpiValueHistory partially : {}, {}", id, monthlyKpiValueHistoryDTO);
        if (monthlyKpiValueHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, monthlyKpiValueHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!monthlyKpiValueHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MonthlyKpiValueHistoryDTO> result = monthlyKpiValueHistoryService.partialUpdate(monthlyKpiValueHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, monthlyKpiValueHistoryDTO.getId().toString())
        );
    }


    /**
     * {@code GET  /monthly-kpi-value-histories/:id} : get the "id" monthlyKpiValueHistory.
     *
     * @param id the id of the monthlyKpiValueHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the monthlyKpiValueHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/monthly-kpi-value-histories/{id}")
    public ResponseEntity<MonthlyKpiValueHistoryDTO> getMonthlyKpiValueHistory(@PathVariable Long id) {
        log.debug("REST request to get MonthlyKpiValueHistory : {}", id);
        Optional<MonthlyKpiValueHistoryDTO> monthlyKpiValueHistoryDTO = monthlyKpiValueHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(monthlyKpiValueHistoryDTO);
    }

    /**
     * {@code DELETE  /monthly-kpi-value-histories/:id} : delete the "id" monthlyKpiValueHistory.
     *
     * @param id the id of the monthlyKpiValueHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/monthly-kpi-value-histories/{id}")
    public ResponseEntity<Void> deleteMonthlyKpiValueHistory(@PathVariable Long id) {
        log.debug("REST request to delete MonthlyKpiValueHistory : {}", id);
        monthlyKpiValueHistoryService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }

    /**
     * {@code GET  /monthly-kpi-value-histories/:id} : get by the given "id" monthlyKpiValue.
     *
     * @param monthlyKpiValueId the id of the monthlyKpiValueHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the monthlyKpiValueHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/monthly-kpi-value-histories")
    public List<MonthlyKpiValueHistoryDTO> getMonthlyKpiValueHistories(@RequestParam(required = true) Long monthlyKpiValueId) {
        log.debug("REST request to get MonthlyKpiValueHistory : {}", monthlyKpiValueId);
        return monthlyKpiValueHistoryService.findByMonthlyKpiValueId(monthlyKpiValueId);
    }

}

