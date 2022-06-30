package ma.itroad.ram.kpi.service.dto;

import ma.itroad.ram.kpi.domain.enumeration.*;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


/**
 * A DTO for the {@link ma.itroad.ram.kpi.domain.Kpi} entity.
 */
public class KpiDetailDTO implements Serializable {

    private Long id;

    private String name;

    private String reference;

/*    private String description;

    private String dirId;

    private Periodicity perdiodicity;*/

    private Integer productionPeriod;

    /*private Status status;

    private Status reminder;

    private KpiDTO inductor;

    private KpiGroupDTO kpiGroup;*/

    private EntiteDTO entite;

  //  private CategoryDTO category;

     //private Boolean isEstimable;

  //  private Boolean isInductor;

   // private Source source;

    // EstimationMethod estimationMethod;

    //private EstimationPeriod estimationPeriod;

  //  private Integer estimationPeriodValue;

    private List<MonthlyKpiValueDTO> refYear1 = new ArrayList<>();
    private List<MonthlyKpiValueDTO> refYear2 = new ArrayList<>();
    private List<MonthlyKpiValueDTO> budgetMonthlyKpiValues = new ArrayList<>();
    private List<MonthlyKpiValueDTO> realisedMonthlyKpiValues = new ArrayList<>();
    private List<MonthlyKpiValueDTO> stoppedMonthlyKpiValues = new ArrayList<>();
    private List<MonthlyKpiValueDTO> ecarts = new ArrayList<>();
    private List<MonthlyKpiValueDTO> ecartsRefYear1 = new ArrayList<>();
    private List<MonthlyKpiValueDTO> ecartsRefYear2 = new ArrayList<>();
    private Double totalRefYear1;
    private Double totalRefYear2;
    private Double totalBudget;
    private Double totalRealised;
    private Double totalStopped;
    private Double totalEcarts;
    private Double totalEcartsRefYear1;
    private Double totalEcartsRefYear2;

    private String creationDate;
    private String lastModifiedBy;
    private String lastModifiedDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

/*    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirId() {
        return dirId;
    }

    public void setDirId(String dirId) {
        this.dirId = dirId;
    }

    public Periodicity getPerdiodicity() {
        return perdiodicity;
    }

    public void setPerdiodicity(Periodicity perdiodicity) {
        this.perdiodicity = perdiodicity;
    }*/

    public Integer getProductionPeriod() {
        return productionPeriod;
    }

    public void setProductionPeriod(Integer productionPeriod) {
        this.productionPeriod = productionPeriod;
    }

   /* public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getReminder() {
        return reminder;
    }

    public void setReminder(Status reminder) {
        this.reminder = reminder;
    }

    public KpiDTO getInductor() {
        return inductor;
    }

    public void setInductor(Boolean inductor) {
        isInductor = inductor;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public EstimationMethod getEstimationMethod() {
        return estimationMethod;
    }

    public void setEstimationMethod(EstimationMethod estimationMethod) {
        this.estimationMethod = estimationMethod;
    }

    public EstimationPeriod getEstimationPeriod() {
        return estimationPeriod;
    }

    public void setEstimationPeriod(EstimationPeriod estimationPeriod) {
        this.estimationPeriod = estimationPeriod;
    }

    public Integer getEstimationPeriodValue() {
        return estimationPeriodValue;
    }

    public void setEstimationPeriodValue(Integer estimationPeriodValue) {
        this.estimationPeriodValue = estimationPeriodValue;
    }*/

    public List<MonthlyKpiValueDTO> getRefYear1() {
        return refYear1;
    }

    public void setRefYear1(List<MonthlyKpiValueDTO> refYear1) {
        this.refYear1 = refYear1;
    }

    public List<MonthlyKpiValueDTO> getRefYear2() {
        return refYear2;
    }

    public void setRefYear2(List<MonthlyKpiValueDTO> refYear2) {
        this.refYear2 = refYear2;
    }

    public List<MonthlyKpiValueDTO> getBudgetMonthlyKpiValues() {
        return budgetMonthlyKpiValues;
    }

    public void setBudgetMonthlyKpiValues(List<MonthlyKpiValueDTO> budgetMonthlyKpiValues) {
        this.budgetMonthlyKpiValues = budgetMonthlyKpiValues;
    }

    public List<MonthlyKpiValueDTO> getRealisedMonthlyKpiValues() {
        return realisedMonthlyKpiValues;
    }

    public void setRealisedMonthlyKpiValues(List<MonthlyKpiValueDTO> realisedMonthlyKpiValues) {
        this.realisedMonthlyKpiValues = realisedMonthlyKpiValues;
    }

    public List<MonthlyKpiValueDTO> getStoppedMonthlyKpiValues() {
        return stoppedMonthlyKpiValues;
    }

    public void setStoppedMonthlyKpiValues(List<MonthlyKpiValueDTO> stoppedMonthlyKpiValues) {
        this.stoppedMonthlyKpiValues = stoppedMonthlyKpiValues;
    }

    public List<MonthlyKpiValueDTO> getEcarts() {
        return ecarts;
    }

    public void setEcarts(List<MonthlyKpiValueDTO> ecarts) {
        this.ecarts = ecarts;
    }

    public List<MonthlyKpiValueDTO> getEcartsRefYear1() {
        return ecartsRefYear1;
    }

    public void setEcartsRefYear1(List<MonthlyKpiValueDTO> ecartsRefYear1) {
        this.ecartsRefYear1 = ecartsRefYear1;
    }

    public List<MonthlyKpiValueDTO> getEcartsRefYear2() {
        return ecartsRefYear2;
    }

    public void setEcartsRefYear2(List<MonthlyKpiValueDTO> ecartsRefYear2) {
        this.ecartsRefYear2 = ecartsRefYear2;
    }

    public Double getTotalRefYear1() {
        return totalRefYear1;
    }

    public void setTotalRefYear1(Double totalRefYear1) {
        this.totalRefYear1 = totalRefYear1;
    }

    public Double getTotalRefYear2() {
        return totalRefYear2;
    }

    public void setTotalRefYear2(Double totalRefYear2) {
        this.totalRefYear2 = totalRefYear2;
    }

    public Double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(Double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public Double getTotalRealised() {
        return totalRealised;
    }

    public void setTotalRealised(Double totalRealised) {
        this.totalRealised = totalRealised;
    }

    public Double getTotalStopped() {
        return totalStopped;
    }

    public void setTotalStopped(Double totalStopped) {
        this.totalStopped = totalStopped;
    }

    public Double getTotalEcarts() {
        return totalEcarts;
    }

    public void setTotalEcarts(Double totalEcarts) {
        this.totalEcarts = totalEcarts;
    }

    public Double getTotalEcartsRefYear1() {
        return totalEcartsRefYear1;
    }

    public void setTotalEcartsRefYear1(Double totalEcartsRefYear1) {
        this.totalEcartsRefYear1 = totalEcartsRefYear1;
    }

    public Double getTotalEcartsRefYear2() {
        return totalEcartsRefYear2;
    }

    public void setTotalEcartsRefYear2(Double totalEcartsRefYear2) {
        this.totalEcartsRefYear2 = totalEcartsRefYear2;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

/*    public void setInductor(KpiDTO inductor) {
        this.inductor = inductor;
    }

    public KpiGroupDTO getKpiGroup() {
        return kpiGroup;
    }

    public void setKpiGroup(KpiGroupDTO kpiGroup) {
        this.kpiGroup = kpiGroup;
    }*/

    public EntiteDTO getEntite() {
        return entite;
    }

    public void setEntite(EntiteDTO entite) {
        this.entite = entite;
    }

  /*  public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }*/

/*    public Boolean getEstimable() {
        return isEstimable;
    }

    public void setEstimable(Boolean estimable) {
        isEstimable = estimable;
    }*/

    public Double getSumOfMonthlyKpiValues(List<MonthlyKpiValueDTO> monthlyKpiValues) {
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        if (monthlyKpiValues != null) {
            monthlyKpiValues.forEach(m -> {
                total.updateAndGet(v -> v + m.getValue());
            });
        }
        return total.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KpiDetailDTO)) {
            return false;
        }

        KpiDetailDTO kpiDTO = (KpiDetailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, kpiDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore


}
