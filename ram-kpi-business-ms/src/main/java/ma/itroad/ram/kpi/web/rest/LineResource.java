package ma.itroad.ram.kpi.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ma.itroad.ram.kpi.repository.LineRepository;
import ma.itroad.ram.kpi.service.LineService;
import ma.itroad.ram.kpi.service.dto.LineDTO;
import ma.itroad.ram.kpi.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ma.itroad.ram.kpi.domain.Line}.
 */
@RestController
@RequestMapping("/api")
public class LineResource {

    private final Logger log = LoggerFactory.getLogger(LineResource.class);

    private static final String ENTITY_NAME = "line";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LineService lineService;

    private final LineRepository lineRepository;

    public LineResource(LineService lineService, LineRepository lineRepository) {
        this.lineService = lineService;
        this.lineRepository = lineRepository;
    }

    /**
     * {@code POST  /lines} : Create a new line.
     *
     * @param lineDTO the lineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lineDTO, or with status {@code 400 (Bad Request)} if the line has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lines")
    public ResponseEntity<LineDTO> createLine(@RequestBody LineDTO lineDTO) throws URISyntaxException {
        log.debug("REST request to save Line : {}", lineDTO);
        if (lineDTO.getId() != null) {
            throw new BadRequestAlertException("A new line cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LineDTO result = lineService.save(lineDTO);
        return ResponseEntity
                .created(new URI("/api/lines/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /lines/:id} : Updates an existing line.
     *
     * @param id      the id of the lineDTO to save.
     * @param lineDTO the lineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lineDTO,
     * or with status {@code 400 (Bad Request)} if the lineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lines/{id}")
    public ResponseEntity<LineDTO> updateLine(@PathVariable(value = "id", required = false) final Long id, @RequestBody LineDTO lineDTO)
            throws URISyntaxException {
        log.debug("REST request to update Line : {}, {}", id, lineDTO);
        if (lineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LineDTO result = lineService.save(lineDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lineDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code PATCH  /lines/:id} : Partial updates given fields of an existing line, field will ignore if it is null
     *
     * @param id      the id of the lineDTO to save.
     * @param lineDTO the lineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lineDTO,
     * or with status {@code 400 (Bad Request)} if the lineDTO is not valid,
     * or with status {@code 404 (Not Found)} if the lineDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the lineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lines/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<LineDTO> partialUpdateLine(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody LineDTO lineDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Line partially : {}, {}", id, lineDTO);
        if (lineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LineDTO> result = lineService.partialUpdate(lineDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lineDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /lines} : get all the lines.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lines in body.
     */
    @GetMapping("/lines")
    public List<LineDTO> getAllLines() {
        log.debug("REST request to get all Lines");
        return lineService.findAll();
    }

    /**
     * {@code GET  /lines/:id} : get the "id" line.
     *
     * @param id the id of the lineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lines/{id}")
    public ResponseEntity<LineDTO> getLine(@PathVariable Long id) {
        log.debug("REST request to get Line : {}", id);
        Optional<LineDTO> lineDTO = lineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lineDTO);
    }

    /**
     * {@code DELETE  /lines/:id} : delete the "id" line.
     *
     * @param id the id of the lineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lines/{id}")
    public ResponseEntity<Void> deleteLine(@PathVariable Long id) {
        log.debug("REST request to delete Line : {}", id);
        lineService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
