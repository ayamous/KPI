package ma.itroad.ram.kpi.repository;

import ma.itroad.ram.kpi.domain.Entite;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Entite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntiteRepository extends JpaRepository<Entite, Long> {
}
