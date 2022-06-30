package ma.itroad.ram.kpi.repository;

import ma.itroad.ram.kpi.domain.KpiGroup;
import ma.itroad.ram.kpi.service.dto.KpiGroupDTO;
import org.bouncycastle.math.ec.ECPointMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.stream.DoubleStream;

/**
 * Spring Data SQL repository for the KpiGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KpiGroupRepository extends JpaRepository<KpiGroup, Long> {

    @Query("SELECT kpg FROM KpiGroup AS kpg, Assignment AS asg JOIN asg.kpiGroup GROUP BY kpg.id")
    Page<KpiGroup> findAllJoinAssignments(Pageable pageable);

    Page<KpiGroup> findByLabelContainingIgnoreCase(String label, Pageable pageable);

}


//    @Query("SELECT kpg FROM KpiGroup kpg JOIN Assignment a ON a.kpiGroup = kpg.id GROUP BY kpg.id")
