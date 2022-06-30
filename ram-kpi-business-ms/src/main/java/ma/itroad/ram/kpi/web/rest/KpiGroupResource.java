package ma.itroad.ram.kpi.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ma.itroad.ram.kpi.domain.KpiGroup;
import ma.itroad.ram.kpi.repository.KpiGroupRepository;
import ma.itroad.ram.kpi.repository.KpiRepository;
import ma.itroad.ram.kpi.service.KpiGroupService;
import ma.itroad.ram.kpi.service.KpiService;
import ma.itroad.ram.kpi.service.dto.AssignmentWrapperDTO;
import ma.itroad.ram.kpi.service.dto.KpiGroupDTO;
import ma.itroad.ram.kpi.web.rest.errors.BadRequestAlertException;
import ma.itroad.ram.kpi.web.rest.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * REST controller for managing {@link ma.itroad.ram.kpi.domain.KpiGroup}.
 */
@RestController
@RequestMapping("/business/api")
public class KpiGroupResource {

    private final Logger log = LoggerFactory.getLogger(KpiGroupResource.class);

    private static final String ENTITY_NAME = "kpiGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KpiGroupService kpiGroupService;
    private final KpiService kpiService;

    private final KpiGroupRepository kpiGroupRepository;


    public KpiGroupResource(KpiGroupService kpiGroupService, KpiGroupRepository kpiGroupRepository, KpiService kpiService) {
        this.kpiGroupService = kpiGroupService;
        this.kpiGroupRepository = kpiGroupRepository;
        this.kpiService = kpiService;
    }

    /**
     * {@code POST  /kpi-groups} : Create a new kpiGroup.
     *
     * @param kpiGroupDTO the kpiGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kpiGroupDTO, or with status {@code 400 (Bad Request)} if the kpiGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kpi-groups")
    public ResponseEntity<KpiGroupDTO> createKpiGroup(@Valid @RequestBody KpiGroupDTO kpiGroupDTO) throws URISyntaxException {
        log.debug("REST request to save KpiGroup : {}", kpiGroupDTO);
        if (kpiGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new kpiGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }

        System.out.println("kpiGroup");

        KpiGroupDTO result = kpiGroupService.save(kpiGroupDTO);

        return ResponseEntity
                .created(new URI("/api/kpi-groups/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /kpi-groups/:id} : Updates an existing kpiGroup.
     *
     * @param id          the id of the kpiGroupDTO to save.
     * @param kpiGroupDTO the kpiGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kpiGroupDTO,
     * or with status {@code 400 (Bad Request)} if the kpiGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kpiGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kpi-groups/{id}")
    public ResponseEntity<KpiGroupDTO> updateKpiGroup(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody KpiGroupDTO kpiGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update KpiGroup : {}, {}", id, kpiGroupDTO);

        if (!Objects.equals(id, kpiGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (kpiGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!kpiGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KpiGroupDTO result = kpiGroupService.save(kpiGroupDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kpiGroupDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code PATCH  /kpi-groups/:id} : Partial updates given fields of an existing kpiGroup, field will ignore if it is null
     *
     * @param id          the id of the kpiGroupDTO to save.
     * @param kpiGroupDTO the kpiGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kpiGroupDTO,
     * or with status {@code 400 (Bad Request)} if the kpiGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the kpiGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the kpiGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/kpi-groups/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<KpiGroupDTO> partialUpdateKpiGroup(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody KpiGroupDTO kpiGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update KpiGroup partially : {}, {}", id, kpiGroupDTO);
        if (kpiGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kpiGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kpiGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KpiGroupDTO> result = kpiGroupService.partialUpdate(kpiGroupDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kpiGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /kpi-groups} : get all the kpiGroups.
     *
     * @param page the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kpiGroups in body.
     */
    @GetMapping("/kpi-groups")
    public ResponseEntity<Page<KpiGroupDTO>> getAllKpiGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String label,
            @RequestParam(defaultValue = "label") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        log.debug("REST request to get a page of KpiGroups {}", pageable);
        Page<KpiGroupDTO> kpiGroups = kpiGroupService.findAll(label, pageable);


        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), kpiGroups);
        return ResponseEntity.ok().headers(headers).body(kpiGroups);
    }


    /**
     * {@code GET  /kpi-groups/:id} : get the "id" kpiGroup.
     *
     * @param id the id of the kpiGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kpiGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kpi-groups/{id}")
    public ResponseEntity<KpiGroupDTO> getKpiGroup(@PathVariable Long id) {
        log.debug("REST request to get KpiGroup : {}", id);
        Optional<KpiGroupDTO> kpiGroupDTO = kpiGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kpiGroupDTO);
    }

    /**
     * {@code DELETE  /kpi-groups/:id} : delete the "id" kpiGroup.
     *
     * @param id the id of the kpiGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kpi-groups/{id}")
    public ResponseEntity<Void> deleteKpiGroup(@PathVariable Long id) {
        log.debug("REST request to delete KpiGroup : {}", id);
        kpiGroupService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }


    /**
     * {@code DELETE  /kpi-groups/:kpiGroupId/kpis/:kpiId} : delete the the association betwwen kpi and kpiGroup.
     *
     * @param kpiGroupId the id of the releated kpiGroupDTO.
     * @param kpiId      the id of the releated kpi to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */

    @DeleteMapping("/kpi-groups/{kpiGroupId}/kpis/{kpiId}")
    public ResponseEntity<?> deleteKpi(@PathVariable(value = "kpiGroupId") Long kpiGroupId,
                                       @PathVariable(value = "kpiId") Long kpiId) throws ResourceNotFoundException {
        return kpiService.findByIdAndkpiGroupId(kpiId, kpiGroupId).map(kpiDto -> {
            kpiDto.setKpiGroup(null);
            kpiService.save(kpiDto);
            return ResponseEntity
                    .noContent()
                    .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, kpiId.toString()))
                    .build();
        }).orElseThrow(() -> new ResourceNotFoundException("Kpi not found with id " + kpiId + " and kpiGroupId " + kpiGroupId));
    }

    /**
     * {@code GET  /kpi-groups/:id} : get the "id" kpiGroup.
     *
     * @param page the id of the kpiGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kpiGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kpi-groups/assignments")
    public ResponseEntity<List<AssignmentWrapperDTO>> getKpiGroupAndAssignments(HttpServletRequest request,
                                                                                @RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "5") int size,
                                                                                @RequestParam(defaultValue = "label") String sortBy,
                                                                                @RequestParam(defaultValue = "DESC") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        log.debug("REST request to get a page of SystemSources {}", pageable);
        Page<KpiGroupDTO> kpiGroups = kpiGroupService.findAllJoinAssignments(pageable);

        String token = "";
        final String authorizationHeaderValue = request.getHeader("Authorization");
        if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Bearer")) {
            token = authorizationHeaderValue.substring(7, authorizationHeaderValue.length());
        }
        List<AssignmentWrapperDTO> assignmentWrapperDto = kpiGroupService.addUsersDetails(kpiGroups, token);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), kpiGroups);
        return ResponseEntity.ok().headers(headers).body(assignmentWrapperDto);
    }

}
