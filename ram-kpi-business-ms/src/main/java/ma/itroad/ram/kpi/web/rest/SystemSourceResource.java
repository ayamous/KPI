package ma.itroad.ram.kpi.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ma.itroad.ram.kpi.repository.SystemSourceRepository;
import ma.itroad.ram.kpi.service.SystemSourceService;
import ma.itroad.ram.kpi.service.dto.SystemSourceDTO;
import ma.itroad.ram.kpi.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ma.itroad.ram.kpi.domain.SystemSource}.
 */
@RestController
@RequestMapping("/business/api")
public class SystemSourceResource {

    private final Logger log = LoggerFactory.getLogger(SystemSourceResource.class);

    private static final String ENTITY_NAME = "systemSource";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SystemSourceService systemSourceService;

    private final SystemSourceRepository systemSourceRepository;

    public SystemSourceResource(SystemSourceService systemSourceService, SystemSourceRepository systemSourceRepository) {
        this.systemSourceService = systemSourceService;
        this.systemSourceRepository = systemSourceRepository;
    }

    /**
     * {@code POST  /system-sources} : Create a new systemSource.
     *
     * @param systemSourceDTO the systemSourceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new systemSourceDTO, or with status {@code 400 (Bad Request)} if the systemSource has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/system-sources")
    public ResponseEntity<SystemSourceDTO> createSystemSource(@RequestBody SystemSourceDTO systemSourceDTO) throws URISyntaxException {
        log.debug("REST request to save SystemSource : {}", systemSourceDTO);
        if (systemSourceDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemSource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemSourceDTO result = systemSourceService.save(systemSourceDTO);
        return ResponseEntity
                .created(new URI("/api/system-sources/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /system-sources/:id} : Updates an existing systemSource.
     *
     * @param id              the id of the systemSourceDTO to save.
     * @param systemSourceDTO the systemSourceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemSourceDTO,
     * or with status {@code 400 (Bad Request)} if the systemSourceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the systemSourceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/system-sources/{id}")
    public ResponseEntity<SystemSourceDTO> updateSystemSource(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody SystemSourceDTO systemSourceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SystemSource : {}, {}", id, systemSourceDTO);
        if (systemSourceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, systemSourceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!systemSourceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SystemSourceDTO result = systemSourceService.save(systemSourceDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, systemSourceDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code PATCH  /system-sources/:id} : Partial updates given fields of an existing systemSource, field will ignore if it is null
     *
     * @param id              the id of the systemSourceDTO to save.
     * @param systemSourceDTO the systemSourceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemSourceDTO,
     * or with status {@code 400 (Bad Request)} if the systemSourceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the systemSourceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the systemSourceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/system-sources/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<SystemSourceDTO> partialUpdateSystemSource(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody SystemSourceDTO systemSourceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SystemSource partially : {}, {}", id, systemSourceDTO);
        if (systemSourceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, systemSourceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!systemSourceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SystemSourceDTO> result = systemSourceService.partialUpdate(systemSourceDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, systemSourceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /system-sources} : get all the systemSources.
     *
     * @param page the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of systemSources in body.
     */
    @GetMapping("/system-sources")
    public ResponseEntity<Page<SystemSourceDTO>> getAllSystemSources(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "5") int size,
                                                                     @RequestParam(defaultValue = "label") String sortBy,
                                                                     @RequestParam(defaultValue = "DESC") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        log.debug("REST request to get a page of SystemSources {}", pageable);
        Page<SystemSourceDTO> systemSources = systemSourceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), systemSources);
        return ResponseEntity.ok().headers(headers).body(systemSources);
    }

    /**
     * {@code GET  /system-sources/:id} : get the "id" systemSource.
     *
     * @param id the id of the systemSourceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the systemSourceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/system-sources/{id}")
    public ResponseEntity<SystemSourceDTO> getSystemSource(@PathVariable Long id) {
        log.debug("REST request to get SystemSource : {}", id);
        Optional<SystemSourceDTO> systemSourceDTO = systemSourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemSourceDTO);
    }

    /**
     * {@code DELETE  /system-sources/:id} : delete the "id" systemSource.
     *
     * @param id the id of the systemSourceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/system-sources/{id}")
    public ResponseEntity<Void> deleteSystemSource(@PathVariable Long id) {
        log.debug("REST request to delete SystemSource : {}", id);
        systemSourceService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
