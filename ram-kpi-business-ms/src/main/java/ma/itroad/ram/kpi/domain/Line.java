package ma.itroad.ram.kpi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

import ma.itroad.ram.kpi.domain.enumeration.KpiValueType;
import ma.itroad.ram.kpi.domain.enumeration.Status;

/**
 * A Line.
 */
@Entity
@Table(name = "line")
public class Line implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "version")
    private KpiValueType version;

    @Column(name = "jhi_from")
    private LocalDate from;

    @Column(name = "jhi_to")
    private LocalDate to;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    /*@JsonIgnoreProperties(value = {"lines"}, allowSetters = true)// not working*/
    @JsonIgnore
    @JoinColumn(name = "period_id")
    private Period period;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Line id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public KpiValueType getVersion() {
        return this.version;
    }

    public Line version(KpiValueType version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(KpiValueType version) {
        this.version = version;
    }

    public LocalDate getFrom() {
        return this.from;
    }

    public Line from(LocalDate from) {
        this.setFrom(from);
        return this;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return this.to;
    }

    public Line to(LocalDate to) {
        this.setTo(to);
        return this;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public Status getStatus() {
        return this.status;
    }

    public Line status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Period getPeriod() {
        return this.period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Line period(Period period) {
        this.setPeriod(period);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Line)) {
            return false;
        }
        return id != null && id.equals(((Line) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Line{" +
                "id=" + getId() +
                ", version='" + getVersion() + "'" +
                ", from='" + getFrom() + "'" +
                ", to='" + getTo() + "'" +
                ", status='" + getStatus() + "'" +
                "}";
    }
}
