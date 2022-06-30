package ma.itroad.ram.kpi.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import ma.itroad.ram.kpi.domain.enumeration.KpiValueType;
import ma.itroad.ram.kpi.repository.MonthlyKpiValueRepository;
import ma.itroad.ram.kpi.service.KpiRequestValidator;
import ma.itroad.ram.kpi.service.MonthlyKpiValueService;
import ma.itroad.ram.kpi.service.dto.KpiDTO;
import ma.itroad.ram.kpi.service.dto.KpiRequestValidationErrorsDTO;
import ma.itroad.ram.kpi.service.dto.MonthlyKpiValueDTO;
import ma.itroad.ram.kpi.web.rest.errors.BadRequestAlertException;
import ma.itroad.ram.kpi.web.rest.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ma.itroad.ram.kpi.domain.MonthlyKpiValue}.
 */
@RestController
@RequestMapping("/business/api")
public class MonthlyKpiValueResource {

    private final Logger log = LoggerFactory.getLogger(MonthlyKpiValueResource.class);

    private static final String ENTITY_NAME = "monthlyKpiValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MonthlyKpiValueService monthlyKpiValueService;

    private final MonthlyKpiValueRepository monthlyKpiValueRepository;

    private final KpiRequestValidator kpiRequestValidator;


    public MonthlyKpiValueResource(MonthlyKpiValueService monthlyKpiValueService, MonthlyKpiValueRepository monthlyKpiValueRepository, KpiRequestValidator kpiRequestValidator) {
        this.monthlyKpiValueService = monthlyKpiValueService;
        this.monthlyKpiValueRepository = monthlyKpiValueRepository;
        this.kpiRequestValidator = kpiRequestValidator;
    }

    /**
     * {@code POST  /monthly-kpi-values} : Create a new monthlyKpiValue.
     *
     * @param monthlyKpiValuesDTO the monthlyKpiValueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new monthlyKpiValueDTO, or with status {@code 400 (Bad Request)} if the monthlyKpiValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/monthly-kpi-values")
    public ResponseEntity<List<?>> createMonthlyKpiValue(@RequestBody List<MonthlyKpiValueDTO> monthlyKpiValuesDTO)
            throws URISyntaxException, ResourceNotFoundException, IOException {
        log.debug("REST request to save MonthlyKpiValue : {}", monthlyKpiValuesDTO);

        for (MonthlyKpiValueDTO monthlyKpiValueDTO : monthlyKpiValuesDTO) {
            if (monthlyKpiValueDTO.getId() != null) {
                throw new BadRequestAlertException("A new monthlyKpiValues cannot already have an ID", ENTITY_NAME, "idexists");
            }
        }
        List<MonthlyKpiValueDTO> results = new ArrayList<>();
        // We must send errors validation list
        for (MonthlyKpiValueDTO monthlyKpiValueDTO : monthlyKpiValuesDTO) {
                MonthlyKpiValueDTO result = monthlyKpiValueService.save(monthlyKpiValueDTO);
                results.add(result);
        }

         return ResponseEntity
                    .ok()
                    //.headers(responseHeaders)
                    .body(results);

    }

    /**
     * {@code PUT  /monthly-kpi-values/:id} : Updates an existing monthlyKpiValue.
     *
     * @param monthlyKpiValuesDTO the monthlyKpiValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monthlyKpiValueDTO,
     * or with status {@code 400 (Bad Request)} if the monthlyKpiValueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the monthlyKpiValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/monthly-kpi-values/totalUpdate")
    public ResponseEntity<List<?>> updateMonthlyKpiValue(
            @RequestBody List<MonthlyKpiValueDTO> monthlyKpiValuesDTO
    ) throws URISyntaxException, IOException, ResourceNotFoundException {
        log.debug("REST request to update MonthlyKpiValue : {} ", monthlyKpiValuesDTO);

        for (MonthlyKpiValueDTO monthlyKpiValueDTO : monthlyKpiValuesDTO) {
            if (monthlyKpiValueDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!monthlyKpiValueRepository.existsById(monthlyKpiValueDTO.getId())) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }
        }

        List<MonthlyKpiValueDTO> results = new ArrayList<>();
        // We must send errors validation list
        List<KpiRequestValidationErrorsDTO> errors = new ArrayList<>();

        for (MonthlyKpiValueDTO monthlyKpiValueDTO : monthlyKpiValuesDTO) {
            KpiRequestValidationErrorsDTO validationError = kpiRequestValidator.validate(monthlyKpiValueDTO);
            System.out.println("Validation errors  : " + validationError);
            if (validationError != null) {
                errors.add(validationError);
            } else {
                MonthlyKpiValueDTO result = monthlyKpiValueService.save(monthlyKpiValueDTO);
                results.add(result);
            }
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        if(errors.size() > 0){
            responseHeaders.set("HttpResponseStatus", "FAILURE");
            return ResponseEntity
                    .ok()
                    .headers(responseHeaders)
                    .body(errors);
        }else{
            responseHeaders.set("HttpResponseStatus", "SUCCESS");
            return ResponseEntity
                    .ok()
                    .headers(responseHeaders)
                    .body(results);
        }


    }

    /**
     * {@code PATCH  /monthly-kpi-values/:id} : Partial updates given fields of an existing monthlyKpiValue, field will ignore if it is null
     *
     * @param monthlyKpiValuesDTO the monthlyKpiValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monthlyKpiValueDTO,
     * or with status {@code 400 (Bad Request)} if the monthlyKpiValueDTO is not valid,
     * or with status {@code 404 (Not Found)} if the monthlyKpiValueDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the monthlyKpiValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/monthly-kpi-values/partialUpdate", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<List<MonthlyKpiValueDTO>> partialUpdateMonthlyKpiValue(
            @RequestBody List<MonthlyKpiValueDTO> monthlyKpiValuesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MonthlyKpiValue partially : {}", monthlyKpiValuesDTO);

        for (MonthlyKpiValueDTO monthlyKpiValueDTO : monthlyKpiValuesDTO) {
            if (monthlyKpiValueDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!monthlyKpiValueRepository.existsById(monthlyKpiValueDTO.getId())) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }
        }

        List<MonthlyKpiValueDTO> results = new ArrayList<>();
        for (MonthlyKpiValueDTO monthlyKpiValueDTO : monthlyKpiValuesDTO) {
            Optional<MonthlyKpiValueDTO> result = monthlyKpiValueService.partialUpdate(monthlyKpiValueDTO);

            if (!result.isPresent()) {
                throw new BadRequestAlertException("there is an element whose value does not exist", ENTITY_NAME, "idnull");
            }
            results.add(result.get());
        }
        return ResponseUtil.wrapOrNotFound(
                Optional.of(results),
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, "partialUpdate")
        );
    }

    /**
     * {@code GET  /monthly-kpi-values} : get all the monthlyKpiValues.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of monthlyKpiValues in body.
     */
    @GetMapping("/monthly-kpi-values")
    public ResponseEntity<List<MonthlyKpiValueDTO>> getAllMonthlyKpiValues(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of MonthlyKpiValues");
        Page<MonthlyKpiValueDTO> page = monthlyKpiValueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /monthly-kpi-values/:id} : get the "id" monthlyKpiValue.
     *
     * @param id the id of the monthlyKpiValueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the monthlyKpiValueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/monthly-kpi-values/{id}")
    public ResponseEntity<MonthlyKpiValueDTO> getMonthlyKpiValue(@PathVariable Long id) {
        log.debug("REST request to get MonthlyKpiValue : {}", id);
        Optional<MonthlyKpiValueDTO> monthlyKpiValueDTO = monthlyKpiValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(monthlyKpiValueDTO);
    }

    /**
     * {@code DELETE  /monthly-kpi-values/:id} : delete the "id" monthlyKpiValue.
     *
     * @param id the id of the monthlyKpiValueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/monthly-kpi-values/{id}")
    public ResponseEntity<Void> deleteMonthlyKpiValue(@PathVariable Long id) {
        log.debug("REST request to delete MonthlyKpiValue : {}", id);
        monthlyKpiValueService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }


    @PostMapping("/monthly-kpi-values/import/xlsx")
    public ResponseEntity<List<MonthlyKpiValueDTO>> importMonthlyKpiValues(@RequestPart(required = true) String kpiValueType,@RequestParam("file") MultipartFile file) throws JsonProcessingException {
      Gson gson = new Gson();
        KpiValueType kpiValType = gson.fromJson(kpiValueType, KpiValueType.class);
        //KpiValueType kpiValType  = new ObjectMapper().readValue(kpiValueType, KpiValueType.class);
        System.out.println("Kpi Value type : " + kpiValType);
        List<MonthlyKpiValueDTO> monthlyKpiValueDtos = monthlyKpiValueService.importMonthlyKpiValues(file, kpiValType);
        return ResponseEntity.ok().body(monthlyKpiValueDtos);
    }


    @PostMapping("/monthly-kpi-values/validation")
    public ResponseEntity<List<?>> createMonthlyKpiValueWithValidation(@RequestBody List<MonthlyKpiValueDTO> monthlyKpiValuesDTO)
            throws URISyntaxException, ResourceNotFoundException, IOException {
        log.debug("REST request to save MonthlyKpiValue : {}", monthlyKpiValuesDTO);

        for (MonthlyKpiValueDTO monthlyKpiValueDTO : monthlyKpiValuesDTO) {
            if (monthlyKpiValueDTO.getId() != null) {
                throw new BadRequestAlertException("A new monthlyKpiValues cannot already have an ID", ENTITY_NAME, "idexists");
            }
        }
        List<MonthlyKpiValueDTO> results = new ArrayList<>();
        // We must send errors validation list
        List<KpiRequestValidationErrorsDTO> errors = new ArrayList<>();

        for (MonthlyKpiValueDTO monthlyKpiValueDTO : monthlyKpiValuesDTO) {
            KpiRequestValidationErrorsDTO validationError = kpiRequestValidator.validate(monthlyKpiValueDTO);
            System.out.println("Validation errors  : " + validationError);
            if (validationError != null) {
                errors.add(validationError);
            } else {
                MonthlyKpiValueDTO result = monthlyKpiValueService.save(monthlyKpiValueDTO);
                results.add(result);
            }
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        if(errors.size() > 0){
            responseHeaders.set("HttpResponseStatus", "FAILURE");
            return ResponseEntity
                    .ok()
                    .headers(responseHeaders)
                    .body(errors);
        }else{
            responseHeaders.set("HttpResponseStatus", "SUCCESS");
            return ResponseEntity
                    .ok()
                    .headers(responseHeaders)
                    .body(results);
        }
    }
}
