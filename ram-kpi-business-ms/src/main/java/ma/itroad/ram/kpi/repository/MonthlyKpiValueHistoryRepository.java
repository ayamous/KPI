package ma.itroad.ram.kpi.repository;

import ma.itroad.ram.kpi.domain.MonthlyKpiValue;
import ma.itroad.ram.kpi.domain.MonthlyKpiValueHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data SQL repository for the MonthlyKpiValueHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonthlyKpiValueHistoryRepository extends JpaRepository<MonthlyKpiValueHistory, Long> {
    List<MonthlyKpiValueHistory> findAllByMonthlyKpiValueId(Long monthlyKpiValueId);
}

