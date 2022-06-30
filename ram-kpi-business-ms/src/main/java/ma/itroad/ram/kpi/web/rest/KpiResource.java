package ma.itroad.ram.kpi.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import ma.itroad.ram.kpi.repository.KpiRepository;
import ma.itroad.ram.kpi.service.DocumentStorageService;
import ma.itroad.ram.kpi.service.KpiService;
import ma.itroad.ram.kpi.service.KpiCriteria;
import ma.itroad.ram.kpi.service.dto.DocumentDTO;
import ma.itroad.ram.kpi.service.dto.KpiDTO;
import ma.itroad.ram.kpi.service.dto.KpiDetailDTO;
import ma.itroad.ram.kpi.service.utils.FileUtils;
import ma.itroad.ram.kpi.web.rest.errors.BadRequestAlertException;
import ma.itroad.ram.kpi.web.rest.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import org.springframework.http.MediaType;


import javax.validation.Valid;

/**
 * REST controller for managing {@link ma.itroad.ram.kpi.domain.Kpi}.
 */
@RestController
@RequestMapping("/business/api")
public class KpiResource {

    private final Logger log = LoggerFactory.getLogger(KpiResource.class);


    private static final String ENTITY_NAME = "kpi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KpiService kpiService;

    private final KpiRepository kpiRepository;


    private final DocumentStorageService documentStorageService;

    public KpiResource(KpiService kpiService, KpiRepository kpiRepository,
                       DocumentStorageService documentStorageService) {
        this.kpiService = kpiService;
        this.kpiRepository = kpiRepository;
        this.documentStorageService = documentStorageService;
    }

    /**
     * {@code POST  /kpis} : Create a new kpi.
     *
     * @param kpiData the kpiDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     * body the new kpiDTO, or with status {@code 400 (Bad Request)} if the
     * kpi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PostMapping("/kpis")
    public ResponseEntity<KpiDTO> createKpiMultipleFiles(@RequestPart String kpiData,
                                                         @RequestParam(name = "documents", required = false) MultipartFile[] documents) throws URISyntaxException, JsonProcessingException {
        log.debug("REST request to save Kpi : {}", kpiData);

       // Gson gson = new Gson();
       // KpiDTO kpiDTO = gson.fromJson(kpiData, KpiDTO.class);
        KpiDTO kpiDTO  = new ObjectMapper().readValue(kpiData, KpiDTO.class);

        if (kpiDTO.getId() != null) {
            throw new BadRequestAlertException("A new kpi cannot already have an ID", ENTITY_NAME, "idexists");
        }

        String dirId = FileUtils.generateFolderId();
        if (documents != null && documents.length > 0 && !documentStorageService.isEmptyMultipartFile(documents)) {
            List<DocumentDTO> documentsDTO = documentStorageService.storeFiles(dirId, documents);
            kpiDTO.setDocuments(documentsDTO);
        }
        kpiDTO.setDirId(dirId);

        KpiDTO kpiDto = kpiService.save(kpiDTO);

        return ResponseEntity
                .created(new URI("/api/kpis/" + kpiDto.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                        kpiDto.getId().toString()))
                .body(kpiDto);
    }

    /**
     * {@code PUT  /kpis/:id} : Updates an existing kpi.
     *
     * @param id      the id of the kpiDTO to save.
     * @param kpiData the kpiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     * the updated kpiDTO,
     * or with status {@code 400 (Bad Request)} if the kpiDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kpiDTO
     * couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kpis/{id}")
    public ResponseEntity<KpiDTO> updateKpi(@PathVariable(value = "id", required = false) final Long id,
                                            @RequestPart String kpiData, @RequestParam(name = "documents", required = false) MultipartFile[] newDocuments)
            throws URISyntaxException, JsonProcessingException {
        log.debug("REST request to update Kpi :  {}", kpiData);

       /* Gson gson = new Gson();
        KpiDTO kpiDTO = gson.fromJson(kpiData, KpiDTO.class);*/

        KpiDTO kpiDTO  = new ObjectMapper().readValue(kpiData, KpiDTO.class);

        if (kpiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kpiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (kpiRepository.existsById(id)) {
            documentStorageService.updateStoreFiles(kpiDTO);
        } else {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        if (newDocuments != null && newDocuments.length > 0 && !documentStorageService.isEmptyMultipartFile(newDocuments)) {
            List<DocumentDTO> documentsDTO = documentStorageService.storeFiles(kpiDTO.getDirId(), newDocuments);
            kpiDTO.getDocuments().addAll(documentsDTO);
        }

        KpiDTO kpiDto = kpiService.save(kpiDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                        kpiDto.getId().toString()))
                .body(kpiDto);
    }

    /**
     * {@code PATCH  /kpis/:id} : Partial updates given fields of an existing kpi,
     * field will ignore if it is null
     *
     * @param id     the id of the kpiDTO to save.
     * @param kpiDTO the kpiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     * the updated kpiDTO,
     * or with status {@code 400 (Bad Request)} if the kpiDTO is not valid,
     * or with status {@code 404 (Not Found)} if the kpiDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the kpiDTO
     * couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/kpis/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<KpiDTO> partialUpdateKpi(@PathVariable(value = "id", required = false) final Long id,
                                                   @RequestBody KpiDTO kpiDTO)
            throws URISyntaxException {
        log.debug("REST request to partial update Kpi partially : {}, {}", id, kpiDTO);
        if (kpiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kpiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kpiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KpiDTO> result = kpiService.partialUpdate(kpiDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kpiDTO.getId().toString()));
    }

    /**
     * {@code GET  /kpis} : get all the kpis.
     *
     * @param page the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     * of kpis in body.
     */
 /*   @GetMapping("/kpis")
    public ResponseEntity<Page<KpiDTO>> getAllKpis(@RequestParam(required = false) Long entiteId,
                                                   @RequestParam(required = false) Long categoryId,
                                                   @Valid @RequestParam(required = false) Status status,
                                                   @Valid @RequestParam(required = false) Status reminder,
                                                   @RequestParam(required = false) String filterBy,
                                                   @RequestParam(required = false) String kpiRefSortOrder,
                                                   @RequestParam(required = false) String kpiNameSortOrder,
                                                   @RequestParam(required = false) Integer year,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size,
                                                   @RequestParam(defaultValue = "name") String sortBy,
                                                   @RequestParam(defaultValue = "DESC") String sortDir) {

        log.debug("REST request to get a page of Kpis entiteId {}", entiteId);

        List<Sort.Order> sorts= new ArrayList<>();
        if (kpiRefSortOrder !=null ) {
            sorts.add(new Sort.Order(kpiRefSortOrder.equalsIgnoreCase("DESC") ?Sort.Direction.DESC:Sort.Direction.ASC,"reference"));
        } else if (kpiNameSortOrder != null ) {
            sorts.add(new Sort.Order(kpiNameSortOrder.equalsIgnoreCase("DESC")? Sort.Direction.DESC:Sort.Direction.ASC,"name"));
        }
       *//* Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();*//*
        Pageable pageable = PageRequest.of(page, size, Sort.by(sorts));
        log.debug("REST request to get a page of Kpis {}", pageable);
        //Page<KpiDTO> kpis = (entiteId == null) ? kpiService.findAll(filterBy,pageable) : kpiService.findByEntiteId(entiteId, pageable);
        Page<KpiDTO> kpis = kpiService.KpisFilter(entiteId, categoryId, status, reminder, filterBy, year, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), kpis);
        return ResponseEntity.ok().headers(headers).body(kpis);
    }*/

    /**
     * {@code GET  /kpis/:id} : get the "id" kpi.
     *
     * @param id the id of the kpiDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     * the kpiDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kpis/{id}")
    public ResponseEntity<KpiDTO> getKpi(@PathVariable Long id) {
        log.debug("REST request to get Kpi : {}", id);
        Optional<KpiDTO> kpiDTO = kpiService.findOne(id);
        kpiDTO.ifPresent(dto -> dto.setMonthlyKpiValues(null));
        return ResponseUtil.wrapOrNotFound(kpiDTO);
    }

    /**
     * {@code DELETE  /kpis/:id} : delete the "id" kpi.
     *
     * @param id the id of the kpiDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kpis/{id}")
    public ResponseEntity<Void> deleteKpi(@PathVariable Long id) {
        log.debug("REST request to delete Kpi : {}", id);
        kpiService.delete(id);

        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }

    @GetMapping("/kpis")
    public ResponseEntity<Page<KpiDTO>> getAllKpisCriteria(KpiCriteria criteria,
                                                           @RequestParam(required = false) String refSortOrder,
                                                           @RequestParam(required = false) String nameSortOrder,
                                                           @RequestParam(required = false) Integer year,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5") int size) throws ParseException {
        log.debug("REST request to get Kpis by criteria : {}", criteria);
        List<Sort.Order> sorts = new ArrayList<>();
        if (refSortOrder != null) {
            sorts.add(new Sort.Order(refSortOrder.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, "reference"));
        }
        if (nameSortOrder != null) {
            sorts.add(new Sort.Order(nameSortOrder.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, "name"));
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sorts));
        log.debug("REST request to get a page of Kpis {}", pageable);
        HttpHeaders responseHeaders = new HttpHeaders();
        Page<KpiDTO> pageResponse = kpiService.findByCriteria(criteria, year, responseHeaders, pageable);
        responseHeaders.forEach((key, value) -> {
            System.out.printf("Header '%s' = %s%n", key, value);
        });

        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), pageResponse);
        return ResponseEntity.ok().headers(responseHeaders).body(pageResponse);
    }


    @GetMapping("/kpis/export/xlsx")
    @ResponseBody
    public ResponseEntity<InputStreamResource> exportXlsxFile(KpiCriteria criteria,
                                                              @RequestParam(required = false) String refSortOrder,
                                                              @RequestParam(required = false) String nameSortOrder,
                                                              @RequestParam(required = false) Integer year,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "300") int size) throws IOException, ParseException {
        String filename = "kpis_realised_" + year + ".xlsx";
        List<Sort.Order> sorts = new ArrayList<>();
        if (refSortOrder != null) {
            sorts.add(new Sort.Order(refSortOrder.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, "reference"));
        }
        if (nameSortOrder != null) {
            sorts.add(new Sort.Order(nameSortOrder.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, "name"));
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sorts));
        log.debug("REST request to get a page of Kpis {}", pageable);
        HttpHeaders responseHeaders = new HttpHeaders();

        Page<KpiDTO> pageResponse = kpiService.findByCriteria(criteria, year, responseHeaders, pageable);
        InputStreamResource file = new InputStreamResource(kpiService.load(pageResponse.getContent(), year));
        //MediaType excelMediaType = new MediaType("application", "vnd.ms-excel");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }


    @GetMapping("/kpis/{id}/details")
    public ResponseEntity<KpiDetailDTO> getKpi(@PathVariable Long id, @RequestParam(required = true) Integer year) throws ResourceNotFoundException {
        log.debug("REST request to get Kpi : {}", id);
        KpiDetailDTO kpiDetailDTO = kpiService.findDetailOne(id, year);
        return ResponseEntity.ok().body(kpiDetailDTO);
    }


    @GetMapping("/kpis/{id}/export/xlsx")
    @ResponseBody
    public ResponseEntity<InputStreamResource> exportKpiDetailToExcelFile(
            @PathVariable(value = "id", required = true) final Long id,
            @RequestParam(required = true) Integer year) throws ResourceNotFoundException {
        // add kpi name in
        String filename = "kpi_" + id + "_" + year + ".xlsx";
        KpiDetailDTO  kpiDetailDTO = kpiService.findDetailOne(id, year);
        InputStreamResource file = new InputStreamResource(kpiService.loadKpiDetailDTO(kpiDetailDTO, year));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }


}