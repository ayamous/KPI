package ma.itroad.ram.kpi.repository;

import ma.itroad.ram.kpi.domain.ControlRule;
import ma.itroad.ram.kpi.domain.Kpi;
import ma.itroad.ram.kpi.service.dto.KpiDTO;
import org.bouncycastle.math.ec.ECPointMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the ControlRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ControlRuleRepository extends JpaRepository<ControlRule, Long> {

    Optional<ControlRule> findByKpiBase(Kpi baseKpi);
}
