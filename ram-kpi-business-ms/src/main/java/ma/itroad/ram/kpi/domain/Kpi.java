package ma.itroad.ram.kpi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import ma.itroad.ram.kpi.domain.audit.Auditable;
import ma.itroad.ram.kpi.domain.date.config.AppConstants;
import ma.itroad.ram.kpi.domain.enumeration.*;

//https://www.geeksforgeeks.org/upload-multiple-files-in-spring-boot-using-jpa-thymeleaf-multipart/
@Entity
@Table(name = "kpi")
public class Kpi extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(unique = true)
    private String reference;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "reminder")
    private Status reminder;

    @Column(name = "dir_id")
    private String dirId;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "perdiodicity")
    private Periodicity perdiodicity;

    /*@Enumerated(EnumType.STRING)
    @Column(name = "calculated_formula")
    private CalculatedFormula calculatedFormula;*/

    @Column(name = "production_period")
    private Integer productionPeriod;

    @Enumerated(EnumType.STRING)
    @Column(name = "estimation_method", length = 1)
    private EstimationMethod estimationMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "source")
    private Source source;

    @Enumerated(EnumType.STRING)
    @Column(name = "estimation_period")
    private EstimationPeriod estimationPeriod;

    @Column(name = "estimation_period_value")
    private Integer estimationPeriodValue;

   /* @Column(name = "production_period")
    private int productionPeriod;*/

    @ManyToOne
    @JsonIgnoreProperties(value = {"monthlyKpiValues", "inductor", "entite", "category", "systemSource", "kpiGroup", "documents"}, allowSetters = true)
    private Kpi inductor;

    @ManyToOne
    @JsonIgnoreProperties(value = {"kpis"}, allowSetters = true)
    private Entite entite;

    @ManyToOne
    @JsonIgnoreProperties(value = {"kpis"}, allowSetters = true)
    private Category category;


    @ManyToMany
    @JoinTable(name = "rel_kpi__system_source", joinColumns = @JoinColumn(name = "kpi_id"),
            inverseJoinColumns = @JoinColumn(name = "system_source_id"))
    @JsonIgnoreProperties(value = {"kpis"}, allowSetters = true)
    private Set<SystemSource> systemSources = new HashSet<>();

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"assignments", "kpis"}, allowSetters = true)
    private KpiGroup kpiGroup;

    @OneToMany(mappedBy = "kpi", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"kpi"}, allowSetters = true)
    List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy = "kpi", fetch = FetchType.LAZY)
    private List<MonthlyKpiValue> monthlyKpiValues  = new ArrayList<>();

    @Column(name = "is_inductor")
    private Boolean isInductor;

    @Column(name = "is_estimable")
    private Boolean isEstimable;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstants.datePatern)
    private Date created;


    public Long getId() {
        return this.id;
    }

    public Kpi id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Kpi name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Status getStatus() {
        return this.status;
    }

    public Kpi status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public Status getReminder() {
        return this.reminder;
    }

    public Kpi reminder(Status reminder) {
        this.setReminder(reminder);
        return this;
    }

    public void setReminder(Status reminder) {
        this.reminder = reminder;
    }


    public String getReference() {
        return this.reference;
    }

    public Kpi reference(String reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDirId() {
        return this.dirId;
    }

    public void setDirId(String dirId) {
        this.dirId = dirId;
    }

    public String getDescription() {
        return this.description;
    }

    public Kpi description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Periodicity getPerdiodicity() {
        return this.perdiodicity;
    }

    public Kpi perdiodicity(Periodicity perdiodicity) {
        this.setPerdiodicity(perdiodicity);
        return this;
    }

    public void setPerdiodicity(Periodicity perdiodicity) {
        this.perdiodicity = perdiodicity;
    }

 /*   public CalculatedFormula getCalculatedFormula() {
        return this.calculatedFormula;
    }

    public Kpi calculatedFormula(CalculatedFormula calculatedFormula) {
        this.setCalculatedFormula(calculatedFormula);
        return this;
    }

    public void setCalculatedFormula(CalculatedFormula calculatedFormula) {
        this.calculatedFormula = calculatedFormula;
    }*/

    public EstimationMethod getEstimationMethod() {
        return this.estimationMethod;
    }

    public Kpi estimationMethod(EstimationMethod estimationMethod) {
        this.setEstimationMethod(estimationMethod);
        return this;
    }

    public void setEstimationMethod(EstimationMethod estimationMethod) {
        this.estimationMethod = estimationMethod;
    }

    public Boolean getIsEstimable() {
        return isEstimable;
    }

    public void setIsEstimable(Boolean isEstimable) {
        this.isEstimable = isEstimable;
    }

    public Kpi isEstimable(Boolean isEstimable) {
        this.setIsEstimable(isEstimable);
        return this;
    }

    public Boolean getIsInductor() {
        return isInductor;
    }

    public void setIsInductor(Boolean isInductor) {
        this.isInductor = isInductor;
    }

    public Kpi isInductor(Boolean isInductor) {
        this.setIsInductor(isInductor);
        return this;
    }

    /*  public int getProductionPeriod() {
        return this.productionPeriod;
    }

    public Kpi productionPeriod(int productionPeriod) {
        this.setProductionPeriod(productionPeriod);
        return this;
    }

    public void setProductionPeriod(int productionPeriod) {
        this.productionPeriod = productionPeriod;
    }*/

    public Integer getProductionPeriod() {
        return this.productionPeriod;
    }

    public Kpi productionPeriod(Integer productionPeriod) {
        this.setProductionPeriod(productionPeriod);
        return this;
    }

    public void setProductionPeriod(Integer productionPeriod) {
        this.productionPeriod = productionPeriod;
    }

    public Kpi getInductor() {
        return this.inductor;
    }

    public void setInductor(Kpi kpi) {
        this.inductor = kpi;
    }

    public Kpi inductor(Kpi kpi) {
        this.setInductor(kpi);
        return this;
    }

    public Entite getEntite() {
        return this.entite;
    }

    public void setEntite(Entite entite) {
        this.entite = entite;
    }

    public Kpi entite(Entite entite) {
        this.setEntite(entite);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Kpi category(Category category) {
        this.setCategory(category);
        return this;
    }

 /*   public SystemSource getSystemSource() {
        return this.systemSource;
    }
    public void setSystemSource(SystemSource systemSource) {
        this.systemSource = systemSource;
    }
    public Kpi systemSource(SystemSource systemSource) {
        this.setSystemSource(systemSource);
        return this;
    }*/

    public Set<SystemSource> getSystemSources() {
        return this.systemSources;
    }

    public void setSystemSources(Set<SystemSource> systemSources) {
        this.systemSources = systemSources;
    }

    public Kpi systemSources(Set<SystemSource> systemSources) {
        this.setSystemSources(systemSources);
        return this;
    }

    public Kpi addSystemSource(SystemSource systemSource) {
        this.systemSources.add(systemSource);
        systemSource.getKpis().add(this);
        return this;
    }

    public Kpi removeSystemSource(SystemSource systemSource) {
        this.systemSources.remove(systemSource);
        systemSource.getKpis().remove(this);
        return this;
    }

    public KpiGroup getKpiGroup() {
        return this.kpiGroup;
    }

    public void setKpiGroup(KpiGroup kpiGroup) {
        this.kpiGroup = kpiGroup;
    }

    public Kpi kpiGroup(KpiGroup kpiGroup) {
        this.setKpiGroup(kpiGroup);
        return this;
    }

    public List<Document> getDocuments() {
        return this.documents;
    }

    public void setDocuments(List<Document> documents) {
        if (this.documents != null) {
            this.documents.forEach(i -> i.setKpi(null));
        }
        if (documents != null) {
            documents.forEach(i -> i.setKpi(this));
        }
        this.documents = documents;
    }

    public Kpi documents(List<Document> documents) {
        this.setDocuments(documents);
        return this;
    }

    public Kpi addDocument(Document document) {
        this.documents.add(document);
        document.setKpi(this);
        return this;
    }

    public Kpi removeDocument(Document document) {
        this.documents.remove(document);
        document.setKpi(null);
        return this;
    }


    public List<MonthlyKpiValue> getMonthlyKpiValues() {
        return this.monthlyKpiValues;
    }

    public void setMonthlyKpiValues(List<MonthlyKpiValue> monthlyKpiValues) {
        if (this.monthlyKpiValues != null) {
            this.monthlyKpiValues.forEach(i -> i.setKpi(null));
        }
        if (monthlyKpiValues != null) {
            monthlyKpiValues.forEach(i -> i.setKpi(this));
        }

        this.monthlyKpiValues = monthlyKpiValues;
    }

    public Kpi monthlyKpiValues(List<MonthlyKpiValue> monthlyKpiValues) {
        this.setMonthlyKpiValues(monthlyKpiValues);
        return this;
    }

    public Kpi resetMonthlyKpiValues(List<MonthlyKpiValue> monthlyKpiValues) {
        this.monthlyKpiValues = new ArrayList<>();
        if (monthlyKpiValues != null) {
            monthlyKpiValues.forEach(i -> {
                this.monthlyKpiValues.add(i);
            });
        }
        return this;
    }

    public Kpi addMonthlyKpiValues(MonthlyKpiValue monthlyKpiValue) {
        this.monthlyKpiValues.add(monthlyKpiValue);
        monthlyKpiValue.setKpi(this);
        return this;
    }

    public Kpi removeMonthlyKpiValues(MonthlyKpiValue monthlyKpiValue) {
        this.monthlyKpiValues.remove(monthlyKpiValue);
        monthlyKpiValue.setKpi(null);
        return this;
    }


    public Source getSource() {
        return this.source;
    }

    public Kpi source(Source source) {
        this.setSource(source);
        return this;
    }

    public void setSource(Source source) {
        this.source = source;
    }


    public EstimationPeriod getEstimationPeriod() {
        return this.estimationPeriod;
    }

    public Kpi estimationPeriod(EstimationPeriod estimationPeriod) {
        this.setEstimationPeriod(estimationPeriod);
        return this;
    }

    public void setEstimationPeriod(EstimationPeriod estimationPeriod) {
        this.estimationPeriod = estimationPeriod;
    }

    public Integer getEstimationPeriodValue() {
        return this.estimationPeriodValue;
    }

    public Kpi estimationPeriodValue(Integer estimationPeriodValue) {
        this.setEstimationPeriodValue(estimationPeriodValue);
        return this;
    }

    public void setEstimationPeriodValue(Integer estimationPeriodValue) {
        this.estimationPeriodValue = estimationPeriodValue;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kpi)) {
            return false;
        }
        return id != null && id.equals(((Kpi) o).id);
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }


    // prettier-ignore
    @Override
    public String toString() {
        return "Kpi{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", reference='" + reference + '\'' +
                ", status=" + status +
                ", reminder=" + reminder +
                ", isEstimable=" + isEstimable +
                ", isInductor=" + isInductor +
                ", dirId='" + dirId + '\'' +
                ", description='" + description + '\'' +
                ", perdiodicity=" + perdiodicity +
                // ", calculatedFormula=" + calculatedFormula +
                ", productionPeriod=" + productionPeriod +
                ", estimationMethod=" + estimationMethod +
                ", source=" + source +
                ", estimationPeriod=" + estimationPeriod +
                ", estimationPeriodValue=" + estimationPeriodValue +
                ", inductor=" + inductor +
                ", entite=" + entite +
                ", category=" + category +
                ", systemSources=" + systemSources +
                ", kpiGroup=" + kpiGroup +
                ", documents=" + documents +
                '}';
    }
}