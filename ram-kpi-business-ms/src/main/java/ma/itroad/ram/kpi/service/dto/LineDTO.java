package ma.itroad.ram.kpi.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import ma.itroad.ram.kpi.domain.enumeration.KpiValueType;
import ma.itroad.ram.kpi.domain.enumeration.Status;

/**
 * A DTO for the {@link ma.itroad.ram.kpi.domain.Line} entity.
 */
public class LineDTO implements Serializable {

    private Long id;

    private KpiValueType version;

    private LocalDate from;

    private LocalDate to;

    private Status status;

    private PeriodDTO period;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public KpiValueType getVersion() {
        return version;
    }

    public void setVersion(KpiValueType version) {
        this.version = version;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PeriodDTO getPeriod() {
        return period;
    }

    public void setPeriod(PeriodDTO period) {
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LineDTO)) {
            return false;
        }

        LineDTO lineDTO = (LineDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, lineDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LineDTO{" +
                "id=" + getId() +
                ", version='" + getVersion() + "'" +
                ", from='" + getFrom() + "'" +
                ", to='" + getTo() + "'" +
                ", status='" + getStatus() + "'" +
                //", period=" + getPeriod() +
                "}";
    }
}
