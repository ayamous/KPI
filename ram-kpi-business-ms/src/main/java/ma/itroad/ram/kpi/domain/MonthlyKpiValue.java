package ma.itroad.ram.kpi.domain;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

import ma.itroad.ram.kpi.domain.enumeration.InsertionMethod;
import ma.itroad.ram.kpi.domain.enumeration.KpiValueType;
import ma.itroad.ram.kpi.domain.enumeration.Status;

/**
 * A MonthlyKpiValue.
 */
@Entity
@Table(name = "monthly_kpi_value", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueDateAndKpiAndVersion", columnNames = {"date", "kpi_id", "type"})})
public class MonthlyKpiValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "date", nullable = false, columnDefinition = "DATE")
    private LocalDate date;


    @Column(name = "value")
    private Double value;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private KpiValueType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")/**/
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private InsertionMethod method;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {
            "monthlyKpiValues", "inductor", "kpiGroup", "entite", "category", "systemSource", "kpiGroup", "documents"},
            allowSetters = true
    )
    @JoinColumn(name = "kpi_id", nullable = false)
    private Kpi kpi;


    @OneToMany(mappedBy = "monthlyKpiValue")
    @JsonIgnoreProperties(value = {"monthlyKpiValue"}, allowSetters = true)
    private List<MonthlyKpiValueHistory> monthlyKpiValueHistories = new ArrayList<>();


    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return this.id;
    }

    public MonthlyKpiValue id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public MonthlyKpiValue label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public MonthlyKpiValue date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getValue() {
        return this.value;
    }

    public MonthlyKpiValue value(Double value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public KpiValueType getType() {
        return this.type;
    }

    public MonthlyKpiValue type(KpiValueType type) {
        this.setType(type);
        return this;
    }

    public void setType(KpiValueType type) {
        this.type = type;
    }

    public Status getStatus() {
        return this.status;
    }

    public MonthlyKpiValue status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonIgnore
    public Kpi getKpi() {
        return this.kpi;
    }

    @JsonProperty
    public void setKpi(Kpi kpi) {
        this.kpi = kpi;
    }

    public MonthlyKpiValue kpi(Kpi kpi) {
        this.setKpi(kpi);
        return this;
    }

    public MonthlyKpiValue() {
    }


    public List<MonthlyKpiValueHistory> getMonthlyKpiValueHistories() {
        return this.monthlyKpiValueHistories;
    }

    public void setMonthlyKpiValueHistories(List<MonthlyKpiValueHistory> monthlyKpiValueHistories) {
        if (this.monthlyKpiValueHistories != null) {
            this.monthlyKpiValueHistories.forEach(i -> i.setMonthlyKpiValue(null));
        }
        if (monthlyKpiValueHistories != null) {
            monthlyKpiValueHistories.forEach(i -> i.setMonthlyKpiValue(this));
        }
        this.monthlyKpiValueHistories = monthlyKpiValueHistories;
    }

    public MonthlyKpiValue monthlyKpiValueHistories(List<MonthlyKpiValueHistory> monthlyKpiValueHistories) {
        this.setMonthlyKpiValueHistories(monthlyKpiValueHistories);
        return this;
    }

    public MonthlyKpiValue addMonthlyKpiValueHistory(MonthlyKpiValueHistory monthlyKpiValueHistory) {
        this.monthlyKpiValueHistories.add(monthlyKpiValueHistory);
        monthlyKpiValueHistory.setMonthlyKpiValue(this);
        return this;
    }

    public MonthlyKpiValue removeMonthlyKpiValueHistory(MonthlyKpiValueHistory monthlyKpiValueHistory) {
        this.monthlyKpiValueHistories.remove(monthlyKpiValueHistory);
        monthlyKpiValueHistory.setMonthlyKpiValue(null);
        return this;
    }


    public InsertionMethod getMethod() {
        return method;
    }

    public void setMethod(InsertionMethod method) {
        this.method = method;
    }

    public MonthlyKpiValue(String label, LocalDate date, Double value, KpiValueType type, Status status,InsertionMethod method, Kpi kpi) {
        this.label = label;
        this.date = date;
        this.value = value;
        this.type = type;
        this.status = status;
        this.method = method;
        this.kpi = kpi;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MonthlyKpiValue)) {
            return false;
        }
        return id != null && id.equals(((MonthlyKpiValue) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "MonthlyKpiValue{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", date=" + date +
                ", value=" + value +
                ", type=" + type +
                ", status=" + status +
                ", kpi=" + kpi +
                '}';
    }
}
