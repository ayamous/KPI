package ma.itroad.ram.kpi.web.rest;

import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import ma.itroad.ram.kpi.domain.KpiGroup;
import ma.itroad.ram.kpi.repository.AssignmentRepository;
import ma.itroad.ram.kpi.repository.KpiGroupRepository;
import ma.itroad.ram.kpi.service.AssignmentService;
import ma.itroad.ram.kpi.service.dto.AssignmentDTO;
import ma.itroad.ram.kpi.service.dto.UserDTO;
import ma.itroad.ram.kpi.service.mapper.KpiGroupMapper;
import ma.itroad.ram.kpi.web.rest.errors.BadRequestAlertException;
import ma.itroad.ram.kpi.web.rest.errors.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * REST controller for managing {@link ma.itroad.ram.kpi.domain.Assignment}.
 */
@RestController
@RequestMapping("/business/api")
public class AssignmentResource {

    private final Logger log = LoggerFactory.getLogger(AssignmentResource.class);

    private static final String ENTITY_NAME = "assignment";

    private static final String USER_ENTITY_NAME = "User";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssignmentService assignmentService;

    private final AssignmentRepository assignmentRepository;
    private final KpiGroupRepository kpiGroupRepository;

    private final KpiGroupMapper kpiGroupMapper;

    public AssignmentResource(AssignmentService assignmentService, AssignmentRepository assignmentRepository,
                              KpiGroupRepository kpiGroupRepository, KpiGroupMapper kpiGroupMapper) {
        this.assignmentService = assignmentService;
        this.assignmentRepository = assignmentRepository;
        this.kpiGroupRepository = kpiGroupRepository;
        this.kpiGroupMapper = kpiGroupMapper;
    }

    /**
     * {@code GET  /assignments} : get all the assignments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     * of assignments in body.
     */
    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentDTO>> getAllAssignments(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Assignments");
        Page<AssignmentDTO> page = assignmentService.findAll(pageable);

        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code POST  /assignments} : Create a new assignment.
     *
     * @param kpiGroupId the assignmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     * body the new assignmentDTO, or with status {@code 400 (Bad Request)}
     * if the assignment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PostMapping("/kpi-groups/{kpiGroupId}/assignments")
    public ResponseEntity<List<AssignmentDTO>> createAssignment(@PathVariable(value = "kpiGroupId") Long kpiGroupId,
                                                                @Valid @RequestBody List<String> usersIds, HttpServletRequest request) {
        log.debug("REST request to save Assignment: {}", usersIds);
        Optional<KpiGroup> kpiGroup = kpiGroupRepository.findById(kpiGroupId);

        if (kpiGroup.get().getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!kpiGroupRepository.existsById(kpiGroupId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        // @TODO add http restemplate interceptor

        List<UserDTO> users = assignmentService.getAllUsers();

        log.debug("Assignment users list : {}", users);

        List<String> keycloakUsersIds = users.stream()
                .map(UserDTO::getId).collect(Collectors.toList());

        Set<String> commonElements = usersIds.stream()
                .distinct()
                .filter(keycloakUsersIds::contains)
                .collect(Collectors.toSet());

        log.debug("Assignment users are all present : {} ", commonElements);
        if (commonElements.size() == 0 || commonElements.size() != usersIds.size()) {
            // Must send the list of exact users ids not found
            throw new UserNotFoundException("User id not found");
        }
        List<AssignmentDTO> assignmentsDto = new ArrayList<>();

        for (String usersId : usersIds) {
            AssignmentDTO assignmentDTO = new AssignmentDTO();
            assignmentDTO.setUserId(usersId);
            assignmentDTO.setKpiGroup(kpiGroupMapper.toDto(kpiGroup.get()));
            assignmentsDto.add(assignmentDTO);
        }

        List<AssignmentDTO> result = assignmentService.saveAll(assignmentsDto);

        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /assignments/:id} : Updates an existing assignment.
     *
     * @param id            the id of the assignmentDTO to save.
     * @param assignmentDTO the assignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     * the updated assignmentDTO, or with status {@code 400 (Bad Request)}
     * if the assignmentDTO is not valid, or with status
     * {@code 500 (Internal Server Error)} if the assignmentDTO couldn't be
     * updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/assignments/{id}")
    public ResponseEntity<AssignmentDTO> updateAssignment(@PathVariable(value = "id", required = false) final Long id,
                                                          @RequestBody AssignmentDTO assignmentDTO) {
        log.debug("REST request to update Assignment : {}, {}", id, assignmentDTO);
        if (assignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AssignmentDTO result = assignmentService.save(assignmentDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                assignmentDTO.getId().toString())).body(result);
    }

    /**
     * {@code PATCH  /assignments/:id} : Partial updates given fields of an existing
     * assignment, field will ignore if it is null
     *
     * @param id            the id of the assignmentDTO to save.
     * @param assignmentDTO the assignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     * the updated assignmentDTO, or with status {@code 400 (Bad Request)}
     * if the assignmentDTO is not valid, or with status
     * {@code 404 (Not Found)} if the assignmentDTO is not found, or with
     * status {@code 500 (Internal Server Error)} if the assignmentDTO
     * couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/assignments/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<AssignmentDTO> partialUpdateAssignment(
            @PathVariable(value = "id", required = false) final Long id, @RequestBody AssignmentDTO assignmentDTO) {
        log.debug("REST request to partial update Assignment partially : {}, {}", id, assignmentDTO);
        if (assignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssignmentDTO> result = assignmentService.partialUpdate(assignmentDTO);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, false,
                ENTITY_NAME, assignmentDTO.getId().toString()));
    }

    /**
     * {@code GET  /assignments/:id} : get the "id" assignment.
     *
     * @param id the id of the assignmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     * the assignmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/assignments/{id}")
    public ResponseEntity<AssignmentDTO> getAssignment(@PathVariable Long id) {
        log.debug("REST request to get Assignment : {}", id);
        Optional<AssignmentDTO> assignmentDTO = assignmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assignmentDTO);
    }

    /**
     * {@code DELETE  /assignments/:id} : delete the "id" assignment.
     *
     * @param id the id of the assignmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/assignments/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        log.debug("REST request to delete Assignment : {}", id);
        assignmentService.delete(id);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }


    /**
     * {@code DELETE  /assignments/kpi-groups/:kpiGroupId/users/userId} : delete user from assignment by the given kpiGroupId and User id.
     *
     * @param kpiGroupId the id of the kpiGroup Assigned to.
     * @param userId     the id of user to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/assignments/kpi-groups/{kpiGroupId}/users/{userId}")
    public ResponseEntity<?> deleteUserFromAssignment(@PathVariable(value = "kpiGroupId") Long kpiGroupId,
                                                      @PathVariable(value = "userId") String userId) {
        assignmentService.deleteAssignments(kpiGroupId, userId);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, userId.toString() + "_" + kpiGroupId.toString()))
                .build();
    }


    @PutMapping("/kpi-groups/{kpiGroupId}/assignments")
    public ResponseEntity<List<AssignmentDTO>> UpdateAssignment(@PathVariable(value = "kpiGroupId") Long kpiGroupId,
                                                                @Valid @RequestBody List<String> usersIds, HttpServletRequest request) {
        log.debug("REST request to save Assignment : {}", usersIds);
        Optional<KpiGroup> kpiGroup = kpiGroupRepository.findById(kpiGroupId);

        if (kpiGroup.get().getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!kpiGroupRepository.existsById(kpiGroupId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        // @TODO add http restemplate interceptor

        List<UserDTO> users = assignmentService.getAllUsers();

        log.debug("Assignment users list : {}", users);

        List<String> keycloakUsersIds = users.stream()
                .map(UserDTO::getId).collect(Collectors.toList());

        Set<String> commonElements = usersIds.stream()
                .distinct()
                .filter(keycloakUsersIds::contains)
                .collect(Collectors.toSet());

        // boolean allArePresent = keycloakUsersIds.retainAll(usersIds);

        log.debug("Assignment users are all present : {} ", commonElements);
        if (commonElements.size() == 0) {
            // Must send the list of exact users ids not found
            throw new UserNotFoundException("User id not found");
        }
        List<AssignmentDTO> assignmentsDto = new ArrayList<>();

        for (String usersId : usersIds) {
            AssignmentDTO assignmentDTO = new AssignmentDTO();
            assignmentDTO.setUserId(usersId);
            assignmentDTO.setKpiGroup(kpiGroupMapper.toDto(kpiGroup.get()));
            assignmentsDto.add(assignmentDTO);
        }

        List<AssignmentDTO> existingAssignmentDtos = assignmentService.findByKpiGroupId(kpiGroupId);

        existingAssignmentDtos.addAll(assignmentsDto);
        List<AssignmentDTO> result = assignmentService.saveAll(assignmentsDto);

        return ResponseEntity.ok().body(result);
    }


    @GetMapping("/assignments/users")
    public ResponseEntity<List<String>> getAllAssignedUsers() {
        log.debug("REST request to get a page of Assignments");
        List<String> List = assignmentService.findAllAssignedUsers();
        return ResponseEntity.ok().body(List);
    }

}

