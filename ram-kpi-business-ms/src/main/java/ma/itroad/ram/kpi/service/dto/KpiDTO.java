package ma.itroad.ram.kpi.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import ma.itroad.ram.kpi.domain.enumeration.*;

/**
 * A DTO for the {@link ma.itroad.ram.kpi.domain.Kpi} entity.
 */
public class KpiDTO implements Serializable {

    private Long id;

    private String name;

    private String reference;

    private String description;

    private String dirId;

    private Periodicity perdiodicity;

    //  private CalculatedFormula calculatedFormula;
    //private int productionPeriod;
    private Integer productionPeriod;

    private Status status;

    private Status reminder;

    private KpiDTO inductor;

    private KpiGroupDTO kpiGroup;

    private EntiteDTO entite;

    private CategoryDTO category;

    private Boolean isEstimable;

    private Boolean isInductor;

    private Source source;

    private EstimationMethod estimationMethod;

    private EstimationPeriod estimationPeriod;

    private Integer estimationPeriodValue;
    // private SystemSourceDTO systemSource;
    private Set<SystemSourceDTO> systemSources = new HashSet<>();

    private List<DocumentDTO> documents = new ArrayList<>();

    private List<MonthlyKpiValueDTO> monthlyKpiValues = new ArrayList<>();

    //private List<MonthlyKpiValueDTO> ecarts = new ArrayList<>();

    private Double totalKpiValues;


    private String creationDate;
    private  String lastModifiedBy;
    private String lastModifiedDate;



    public Boolean getEstimable() {
        return isEstimable;
    }

    public void setEstimable(Boolean estimable) {
        isEstimable = estimable;
    }


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Periodicity getPerdiodicity() {
        return perdiodicity;
    }

    public void setPerdiodicity(Periodicity perdiodicity) {
        this.perdiodicity = perdiodicity;
    }

  /*  public CalculatedFormula getCalculatedFormula() {
        return calculatedFormula;
    }

    public void setCalculatedFormula(CalculatedFormula calculatedFormula) {
        this.calculatedFormula = calculatedFormula;
    }*/

    public EstimationMethod getEstimationMethod() {
        return estimationMethod;
    }

    public void setEstimationMethod(EstimationMethod estimationMethod) {
        this.estimationMethod = estimationMethod;
    }

    /*  public int getProductionPeriod() {
            return productionPeriod;
        }

        public void setProductionPeriod(int productionPeriod) {
            this.productionPeriod = productionPeriod;
        }*/
    public Integer getProductionPeriod() {
        return productionPeriod;
    }

    public void setProductionPeriod(Integer productionPeriod) {
        this.productionPeriod = productionPeriod;
    }

    public KpiDTO getInductor() {
        return inductor;
    }

    public void setInductor(KpiDTO inductor) {
        this.inductor = inductor;
    }

    public KpiGroupDTO getKpiGroup() {
        return kpiGroup;
    }

    public void setKpiGroup(KpiGroupDTO kpiGroup) {
        this.kpiGroup = kpiGroup;
    }

    public EntiteDTO getEntite() {
        return entite;
    }

    public void setEntite(EntiteDTO entite) {
        this.entite = entite;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public Set<SystemSourceDTO> getSystemSources() {
        return systemSources;
    }

    public void setSystemSources(Set<SystemSourceDTO> systemSources) {
        this.systemSources = systemSources;
    }

   /* public SystemSourceDTO getSystemSource() {
        return systemSource;
    }

    public void setSystemSource(SystemSourceDTO systemSource) {
        this.systemSource = systemSource;
    }*/

    public List<DocumentDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentDTO> documents) {
        this.documents = documents;
    }

    public String getDirId() {
        return dirId;
    }

    public void setDirId(String dirId) {
        this.dirId = dirId;
    }

    public Status getStatus() {
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

    public List<MonthlyKpiValueDTO> getMonthlyKpiValues() {
        return monthlyKpiValues;
    }

    public void setMonthlyKpiValues(List<MonthlyKpiValueDTO> monthlyKpiValues) {
        this.monthlyKpiValues = monthlyKpiValues;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public EstimationPeriod getEstimationPeriod() {
        return estimationPeriod;
    }

    public void setEstimationPeriod(EstimationPeriod estimationPeriod) {
        this.estimationPeriod = estimationPeriod;
    }

    //isEstimable
    public Boolean getIsEstimable() {
        return isEstimable;
    }

    public void setIsEstimable(Boolean isEstimable) {
        this.isEstimable = isEstimable;
    }

    public KpiDTO isEstimable(Boolean isEstimable) {
        this.setIsEstimable(isEstimable);
        return this;
    }

    // isInductor
    public Boolean getIsInductor() {
        return isInductor;
    }

    public void setIsInductor(Boolean isInductor) {
        this.isInductor = isInductor;
    }

    public KpiDTO isInductor(Boolean isInductor) {
        this.setIsInductor(isInductor);
        return this;
    }

    public Double getTotalKpiValues() {
        return totalKpiValues;
    }

    public void setTotalKpiValues(Double totalKpiValues) {
        this.totalKpiValues = totalKpiValues;
    }

    public Integer getEstimationPeriodValue() {
        return estimationPeriodValue;
    }

    public void setEstimationPeriodValue(Integer estimationPeriodValue) {
        this.estimationPeriodValue = estimationPeriodValue;
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
        if (!(o instanceof KpiDTO)) {
            return false;
        }

        KpiDTO kpiDTO = (KpiDTO) o;
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

    @Override
    public String toString() {
        return "KpiDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", reference='" + reference + '\'' +
                ", description='" + description + '\'' +
                ", dirId='" + dirId + '\'' +
                ", perdiodicity=" + perdiodicity +
                // ", calculatedFormula=" + calculatedFormula +
                ", productionPeriod=" + productionPeriod +
                ", estimationMethod=" + estimationMethod +
                ", estimationPeriod=" + estimationPeriod +
                ", estimationPeriodValue=" + estimationPeriodValue +
                ", status=" + status +
                ", isInductor=" + isInductor +
                ", reminder=" + reminder +
                ", inductor=" + inductor +
                ", kpiGroup=" + kpiGroup +
                ", entite=" + entite +
                ", category=" + category +
                ", isEstimable=" + isEstimable +
                ", systemSources=" + systemSources +
                ", documents=" + documents +
                ", monthlyKpiValues=" + monthlyKpiValues +
                ", source=" + source +
                '}';
    }
}
