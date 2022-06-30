package ma.itroad.ram.kpi.service;

import java.util.List;
import java.util.Optional;

import ma.itroad.ram.kpi.service.dto.AssignmentDTO;
import ma.itroad.ram.kpi.service.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.itroad.ram.kpi.domain.Assignment}.
 */
public interface AssignmentService {
    /**
     * Save a assignment.
     *
     * @param assignmentDTO the entity to save.
     * @return the persisted entity.
     */
    AssignmentDTO save(AssignmentDTO assignmentDTO);

    /**
     * Partially updates a assignment.
     *
     * @param assignmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AssignmentDTO> partialUpdate(AssignmentDTO assignmentDTO);

    /**
     * Get all the assignments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssignmentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" assignment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AssignmentDTO> findOne(Long id);

    /**
     * Delete the "id" assignment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * saveAll assignments.
     *
     * @param assignments list.
     */
    List<AssignmentDTO> saveAll(List<AssignmentDTO> assignments);


    /**
     * saveAll assignments.
     *
     * @param id user id.
     */

    Optional<UserDTO> getUser(String id, String token);

    List<UserDTO> getAllUsers();


    Page<AssignmentDTO> findAllAssignments(Pageable pageable);


    // Optional<List<AssignmentDTO>> findByKpiGroupIdAndUserId(Long kpiGroupId, String userId);
    //List<AssignmentDTO> findByKpiGroupIdAndUserId(Long kpiGroupId, String userId);

    void deleteAssignments(Long kpiGroupId, String userId);

    List<AssignmentDTO> findByKpiGroupId(Long kpiGroupId);

    List<String> findAllAssignedUsers();
}
