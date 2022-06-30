package ma.itroad.ram.kpi.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ma.itroad.ram.kpi.repository.ControlRuleRepository;
import ma.itroad.ram.kpi.service.ControlRuleService;
import ma.itroad.ram.kpi.service.dto.ControlRuleDTO;
import ma.itroad.ram.kpi.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ma.itroad.ram.kpi.domain.ControlRule}.
 */
@RestController
@RequestMapping("/business/api")
public class ControlRuleResource {

    private final Logger log = LoggerFactory.getLogger(ControlRuleResource.class);

    private static final String ENTITY_NAME = "controlRule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ControlRuleService controlRuleService;

    private final ControlRuleRepository controlRuleRepository;

    public ControlRuleResource(ControlRuleService controlRuleService, ControlRuleRepository controlRuleRepository) {
        this.controlRuleService = controlRuleService;
        this.controlRuleRepository = controlRuleRepository;
    }

    /**
     * {@code POST  /control-rules} : Create a new controlRule.
     *
     * @param controlRuleDTO the controlRuleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new controlRuleDTO, or with status {@code 400 (Bad Request)} if the controlRule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/control-rules")
    public ResponseEntity<ControlRuleDTO> createControlRule(@RequestBody ControlRuleDTO controlRuleDTO) throws URISyntaxException {
        log.debug("REST request to save ControlRule : {}", controlRuleDTO);
        if (controlRuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new controlRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ControlRuleDTO result = controlRuleService.save(controlRuleDTO);
        return ResponseEntity
                .created(new URI("/api/control-rules/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /control-rules/:id} : Updates an existing controlRule.
     *
     * @param id             the id of the controlRuleDTO to save.
     * @param controlRuleDTO the controlRuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controlRuleDTO,
     * or with status {@code 400 (Bad Request)} if the controlRuleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the controlRuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/control-rules/{id}")
    public ResponseEntity<ControlRuleDTO> updateControlRule(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody ControlRuleDTO controlRuleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ControlRule : {}, {}", id, controlRuleDTO);
        if (controlRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, controlRuleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!controlRuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ControlRuleDTO result = controlRuleService.save(controlRuleDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, controlRuleDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code PATCH  /control-rules/:id} : Partial updates given fields of an existing controlRule, field will ignore if it is null
     *
     * @param id             the id of the controlRuleDTO to save.
     * @param controlRuleDTO the controlRuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controlRuleDTO,
     * or with status {@code 400 (Bad Request)} if the controlRuleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the controlRuleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the controlRuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/control-rules/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<ControlRuleDTO> partialUpdateControlRule(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody ControlRuleDTO controlRuleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ControlRule partially : {}, {}", id, controlRuleDTO);
        if (controlRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, controlRuleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!controlRuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ControlRuleDTO> result = controlRuleService.partialUpdate(controlRuleDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, controlRuleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /control-rules} : get all the controlRules.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of controlRules in body.
     */
    @GetMapping("/control-rules")
    public Page<ControlRuleDTO> getAllControlRules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);
        log.debug("REST request to get all ControlRules {}", pageable);
        return controlRuleService.findAll(pageable);
    }

    /**
     * {@code GET  /control-rules/:id} : get the "id" controlRule.
     *
     * @param id the id of the controlRuleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the controlRuleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/control-rules/{id}")
    public ResponseEntity<ControlRuleDTO> getControlRule(@PathVariable Long id) {
        log.debug("REST request to get ControlRule : {}", id);
        Optional<ControlRuleDTO> controlRuleDTO = controlRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(controlRuleDTO);
    }

    /**
     * {@code DELETE  /control-rules/:id} : delete the "id" controlRule.
     *
     * @param id the id of the controlRuleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/control-rules/{id}")
    public ResponseEntity<Void> deleteControlRule(@PathVariable Long id) {
        log.debug("REST request to delete ControlRule : {}", id);
        controlRuleService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
