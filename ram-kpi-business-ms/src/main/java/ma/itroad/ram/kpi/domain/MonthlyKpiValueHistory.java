package ma.itroad.ram.kpi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ma.itroad.ram.kpi.domain.audit.Auditable;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A MonthlyKpiValueHistory.
 */
@Entity
@Table(name = "monthly_kpi_value_history")
public class MonthlyKpiValueHistory extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private Double value;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JsonIgnoreProperties(value = { "monthlyKpiValueHistories", "kpi" }, allowSetters = true)
    private MonthlyKpiValue monthlyKpiValue;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MonthlyKpiValueHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return this.value;
    }

    public MonthlyKpiValueHistory value(Double value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public MonthlyKpiValueHistory date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public MonthlyKpiValue getMonthlyKpiValue() {
        return this.monthlyKpiValue;
    }

    public void setMonthlyKpiValue(MonthlyKpiValue monthlyKpiValue) {
        this.monthlyKpiValue = monthlyKpiValue;
    }

    public MonthlyKpiValueHistory monthlyKpiValue(MonthlyKpiValue monthlyKpiValue) {
        this.setMonthlyKpiValue(monthlyKpiValue);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MonthlyKpiValueHistory)) {
            return false;
        }
        return id != null && id.equals(((MonthlyKpiValueHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MonthlyKpiValueHistory{" +
                "id=" + getId() +
                ", value=" + getValue() +
                ", date='" + getDate() + "'" +
                "}";
    }
}
