package ma.itroad.ram.kpi.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

import ma.itroad.ram.kpi.repository.EntiteRepository;
import ma.itroad.ram.kpi.service.EntiteService;
import ma.itroad.ram.kpi.service.dto.EntiteDTO;
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
 * REST controller for managing {@link ma.itroad.ram.kpi.domain.Entite}.
 */
@RestController
@RequestMapping("/business/api")
public class EntiteResource {

    private final Logger log = LoggerFactory.getLogger(EntiteResource.class);

    private static final String ENTITY_NAME = "entite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntiteService entiteService;

    private final EntiteRepository entiteRepository;

    public EntiteResource(EntiteService entiteService, EntiteRepository entiteRepository) {
        this.entiteService = entiteService;
        this.entiteRepository = entiteRepository;
    }

    /**
     * {@code POST  /entites} : Create a new entite.
     *
     * @param entiteDTO the entiteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entiteDTO, or with status {@code 400 (Bad Request)} if the entite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entites")
    public ResponseEntity<EntiteDTO> createEntite(@RequestBody EntiteDTO entiteDTO) throws URISyntaxException {
        log.debug("REST request to save Entite : {}", entiteDTO);
        if (entiteDTO.getId() != null) {
            throw new BadRequestAlertException("A new entite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntiteDTO result = entiteService.save(entiteDTO);
        return ResponseEntity
                .created(new URI("/api/entites/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /entites/:id} : Updates an existing entite.
     *
     * @param id        the id of the entiteDTO to save.
     * @param entiteDTO the entiteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entiteDTO,
     * or with status {@code 400 (Bad Request)} if the entiteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entiteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entites/{id}")
    public ResponseEntity<EntiteDTO> updateEntite(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody EntiteDTO entiteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Entite : {}, {}", id, entiteDTO);
        if (entiteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entiteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entiteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EntiteDTO result = entiteService.save(entiteDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entiteDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code PATCH  /entites/:id} : Partial updates given fields of an existing entite, field will ignore if it is null
     *
     * @param id        the id of the entiteDTO to save.
     * @param entiteDTO the entiteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entiteDTO,
     * or with status {@code 400 (Bad Request)} if the entiteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the entiteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the entiteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/entites/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<EntiteDTO> partialUpdateEntite(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody EntiteDTO entiteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Entite partially : {}, {}", id, entiteDTO);
        if (entiteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entiteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entiteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EntiteDTO> result = entiteService.partialUpdate(entiteDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entiteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /entites} : get all the entites.
     *
     * @param page, size, sort the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entites in body.
     */
    @GetMapping("/entites")
    //@org.springdoc.api.annotations.ParameterObject
    public ResponseEntity<Page<EntiteDTO>> getAllEntites(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size,
                                                         @RequestParam(defaultValue = "label") String sortBy,
                                                         @RequestParam(defaultValue = "DESC") String sortDir) {


        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        log.debug("REST request to get a page of Entites {}", pageable);

        Page<EntiteDTO> entities = entiteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), entities);
        return ResponseEntity.ok().headers(headers).body(entities);
    }

    /**
     * {@code GET  /entites/:id} : get the "id" entite.
     *
     * @param id the id of the entiteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entiteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entites/{id}")
    public ResponseEntity<EntiteDTO> getEntite(@PathVariable Long id) {
        log.debug("REST request to get Entite : {}", id);
        Optional<EntiteDTO> entiteDTO = entiteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entiteDTO);
    }

    /**
     * {@code DELETE  /entites/:id} : delete the "id" entite.
     *
     * @param id the id of the entiteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entites/{id}")
    public ResponseEntity<Void> deleteEntite(@PathVariable Long id) {
        log.debug("REST request to delete Entite : {}", id);
        entiteService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
