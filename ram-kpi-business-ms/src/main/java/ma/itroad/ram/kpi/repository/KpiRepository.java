package ma.itroad.ram.kpi.repository;

import ma.itroad.ram.kpi.domain.Kpi;
import ma.itroad.ram.kpi.domain.KpiUserReminder;
import ma.itroad.ram.kpi.domain.enumeration.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Spring Data SQL repository for the Kpi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KpiRepository extends JpaRepository<Kpi, Long>, JpaSpecificationExecutor<Kpi> {

    Optional<Kpi> findByIdAndKpiGroupId(Long kpiId, Long kpiGroupId);

    Page<Kpi> findByEntiteId(Long entiteId, Pageable pageable);

    Page<Kpi> findByKpiGroupIdNull(Pageable pageable);

    Page<Kpi> findByCategoryId(Long categoryId, Pageable pageable);

    //, u.reference, u.status, u.reminder , u.isEstimable, u.isInductor, u.dirId , u.description, u.perdiodicity, u.calculatedFormula, u.productionPeriod, u.estimationMethod, u.source, u.estimationPeriod, u.inductor, u.entite, u.category, u.systemSources, u.kpiGroup, u.documents, u.monthlyKpiValues
    @Query("SELECT u.id , u.name " +
            "FROM Kpi u " +
            "WHERE (:entityId IS NULL OR u.entite.id = :entityId )  " +
            "AND (:status IS NULL OR u.status = :status )  " +
            "AND (:filterBy IS NULL OR u.kpiGroup.id IS NULL )  " +
            "AND (:reminder IS NULL OR u.reminder = :reminder)")
    Page<Kpi> KpisFilter(Long entityId, Status status, Status reminder, String filterBy, Pageable pageable);

    Optional<Kpi> findByReference(String s);


    List<Kpi> findAllByProductionPeriodAndStatusAndReminder(int productionPeriod, Status status, Status reminder);

    //@Query("SELECT new ma.itroad.ram.kpi.service.dto.KpiUsersReminderDTO(assg.userId, kpi.id ,kpi.name , kpi.reference) FROM  Kpi kpi inner join Assignment assg ON  assg.kpiGroup.id = kpi.kpiGroup.id and kpi.productionPeriod=:productionPeriod and kpi.status = :status and kpi.reminder = :reminder INNER JOIN  MonthlyKpiValue mkv on mkv.kpi.id = kpi.id and mkv.value > 0 group by kpi.id, assg.userId")
    @Query("SELECT new ma.itroad.ram.kpi.domain.KpiUserReminder(assg.userId, kpi.id ,kpi.name , kpi.reference) FROM  Kpi kpi inner join Assignment assg ON  assg.kpiGroup.id = kpi.kpiGroup.id and kpi.productionPeriod=:productionPeriod and kpi.status = :status and kpi.reminder = :reminder  group by kpi.id, assg.userId")
    List<KpiUserReminder> fetchAll(int productionPeriod, Status reminder, Status status);






    /*@Query("SELECT kpi FROM Kpi AS kpi WHERE  kpi.productionPeriod = :value")
    List<Kpi> fetchAllKpiNeedToBeReminders(Integer value);*/

    /*@Query("select k from Kpi k left join fetch k.monthlyKpiValues m where m.year =:year or m.year is null order by k.name")
    List<Kpi> filterMonthlyValues(@Param(value = "year") Integer year, Pageable pageable);*/
}
