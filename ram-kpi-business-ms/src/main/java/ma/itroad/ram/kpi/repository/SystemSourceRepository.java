package ma.itroad.ram.kpi.repository;

import ma.itroad.ram.kpi.domain.SystemSource;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SystemSource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemSourceRepository extends JpaRepository<SystemSource, Long> {
}
