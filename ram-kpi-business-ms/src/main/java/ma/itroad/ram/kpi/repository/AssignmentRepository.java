package ma.itroad.ram.kpi.repository;

import ma.itroad.ram.kpi.domain.Assignment;
import ma.itroad.ram.kpi.domain.Kpi;
import ma.itroad.ram.kpi.service.dto.AssignmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;

/**
 * Spring Data SQL repository for the Assignment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    @Query("SELECT asg FROM Assignment asg GROUP BY asg.id, asg.kpiGroup")
    Page<Assignment> findAllAssignments(Pageable pageable);

    List<Assignment> findByKpiGroupIdAndUserId(Long kpiGroupId, String userId);

    //@Query("DELETE FROM Assignment asg WHERE asg.kpiGroup= ?1 AND asg.userId = ?2")
    void deleteByKpiGroupIdAndUserId(Long kpiGroupId, String userId);

    List<Assignment> findByKpiGroupId(Long kpiGroupId);

    @Query("SELECT asg.userId FROM Assignment asg")
    List<String> findAllAssignedUsers();

    List<Assignment> findByKpiGroupIdIn(List<Long> kpiGroupsIdList);
}
