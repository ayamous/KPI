package ma.itroad.ram.kpi.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link ma.itroad.ram.kpi.domain.MonthlyKpiValueHistory} entity.
 */
public class MonthlyKpiValueHistoryDTO implements Serializable {

    private Long id;

    private Double value;

    private LocalDate date;

    private MonthlyKpiValueDTO monthlyKpiValue;

    private String creationDate;

    private String creationBy;

    private  String lastModifiedBy;

    private String lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public MonthlyKpiValueDTO getMonthlyKpiValue() {
        return monthlyKpiValue;
    }

    public void setMonthlyKpiValue(MonthlyKpiValueDTO monthlyKpiValue) {
        this.monthlyKpiValue = monthlyKpiValue;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationBy() {
        return creationBy;
    }

    public void setCreationBy(String creationBy) {
        this.creationBy = creationBy;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MonthlyKpiValueHistoryDTO)) {
            return false;
        }
        MonthlyKpiValueHistoryDTO monthlyKpiValueHistoryDTO = (MonthlyKpiValueHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, monthlyKpiValueHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MonthlyKpiValueHistoryDTO{" +
                "id=" + getId() +
                ", value=" + getValue() +
                ", date='" + getDate() + "'" +
                ", monthlyKpiValue=" + getMonthlyKpiValue() +
                "}";
    }
}

