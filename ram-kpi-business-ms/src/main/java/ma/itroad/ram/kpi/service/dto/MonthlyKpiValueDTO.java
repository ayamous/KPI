package ma.itroad.ram.kpi.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import ma.itroad.ram.kpi.domain.enumeration.InsertionMethod;
import ma.itroad.ram.kpi.domain.enumeration.KpiValueType;
import ma.itroad.ram.kpi.domain.enumeration.Status;
import org.hibernate.sql.Insert;

/**
 * A DTO for the {@link ma.itroad.ram.kpi.domain.MonthlyKpiValue} entity.
 */
public class MonthlyKpiValueDTO implements Serializable {

    private Long id;

    private String label;

    private LocalDate date;

    private Double value;

    private KpiValueType type;

    private InsertionMethod method;

    private Status status;
/*    private String kpiName;
    private String kpiReference;*/

    private KpiDTO kpi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public KpiValueType getType() {
        return type;
    }

    public void setType(KpiValueType type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public InsertionMethod getMethod() {
        return method;
    }

    public void setMethod(InsertionMethod method) {
        this.method = method;
    }

    public KpiDTO getKpi() {
        return kpi;
    }

    public void setKpi(KpiDTO kpi) {
        this.kpi = kpi;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MonthlyKpiValueDTO)) {
            return false;
        }

        MonthlyKpiValueDTO monthlyKpiValueDTO = (MonthlyKpiValueDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, monthlyKpiValueDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }


    /*public String getKpiName() {
        return kpiName;
    }

    public void setKpiName(String kpiName) {
        this.kpiName = kpiName;
    }

    public String getKpiReference() {
        return kpiReference;
    }

    public void setKpiReference(String kpiReference) {
        this.kpiReference = kpiReference;
    }*/

    @Override
    public String toString() {
        return "MonthlyKpiValueDTO{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", date=" + date +
                ", value=" + value +
                ", type=" + type +
                ", method=" + method +
                ", status=" + status +
                ", kpi=" + kpi +
                '}';
    }
}
