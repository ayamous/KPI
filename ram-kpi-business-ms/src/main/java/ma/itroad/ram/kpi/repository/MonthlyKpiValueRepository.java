package ma.itroad.ram.kpi.repository;

import ma.itroad.ram.kpi.domain.Kpi;
import ma.itroad.ram.kpi.domain.MonthlyKpiValue;
import ma.itroad.ram.kpi.domain.enumeration.KpiValueType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the MonthlyKpiValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonthlyKpiValueRepository extends JpaRepository<MonthlyKpiValue, Long> {
    //Set<MonthlyKpiValue> findAllByKpiAndDateBetweenOrderByDateDesc(Kpi kpi, LocalDate start, LocalDate end);

    @Query("select m from MonthlyKpiValue m where m.kpi.id =:kpiId and m.date >:start AND m.date < :end order by m.date asc")
    List<MonthlyKpiValue> fetchMonthlyKpiValues(Long kpiId, LocalDate start, LocalDate end);

    Optional<MonthlyKpiValue> findByKpiAndDate(Kpi kpi, LocalDate date);


    @Query("select m from MonthlyKpiValue m where m.kpi.id =:kpiId and m.date >:start AND m.date < :end and m.type = :version")
    List<MonthlyKpiValue> sumValuesByKpiAndDatesBetweenStartAndEnd(Long kpiId, LocalDate start, LocalDate end,  KpiValueType version);


    @Query("select m.value from MonthlyKpiValue m where m.kpi.id =:kpiId and m.date = :date and m.type = :version")
    Double getValueByKpiAndDate(Long kpiId, LocalDate date, KpiValueType version);


    @Query("select m from MonthlyKpiValue m where m.kpi.id =:kpiId and m.type = :version and m.date >:start AND m.date < :end order by m.date asc")
    List<MonthlyKpiValue> fetchMonthlyKpiValuesByVersion(Long kpiId, LocalDate start, LocalDate end, KpiValueType version);
}
