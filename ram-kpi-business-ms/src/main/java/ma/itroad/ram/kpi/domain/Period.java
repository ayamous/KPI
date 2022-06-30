package ma.itroad.ram.kpi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Pattern;

/**
 * A Period.
 */
@Entity
@Table(name = "period")
public class Period implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "year_ref_1", nullable = true)
    @Pattern(regexp = "N-\\b([1-9]|10)\\b$",
            message = "yearRef1 must match N-{followed by number from 1 to 10}")
    private String yearRef1;

    @Column(name = "year_ref_2", nullable = true)
    @Pattern(regexp = "N-\\b([1-9]|10)\\b$",
            message = "yearRef2 must match N-{followed by number from 1 to 10}")
    private String yearRef2;

    @OneToMany(mappedBy = "period", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"period"}, allowSetters = true)
    private List<Line> lines = new ArrayList<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Period id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYearRef1() {
        return this.yearRef1;
    }

    public Period yearRef1(String yearRef1) {
        this.setYearRef1(yearRef1);
        return this;
    }

    public void setYearRef1(String yearRef1) {
        this.yearRef1 = yearRef1;
    }

    public String getYearRef2() {
        return this.yearRef2;
    }

    public Period yearRef2(String yearRef2) {
        this.setYearRef2(yearRef2);
        return this;
    }

    public void setYearRef2(String yearRef2) {
        this.yearRef2 = yearRef2;
    }

    public List<Line> getLines() {
        return this.lines;
    }

    public void setLines(List<Line> lines) {
        if (this.lines != null) {
            this.lines.forEach(i -> i.setPeriod(null));
        }
        if (lines != null) {
            lines.forEach(i -> i.setPeriod(this));
        }
        this.lines = lines;
    }

    public Period lines(List<Line> lines) {
        this.setLines(lines);
        return this;
    }

    public Period addLine(Line line) {
        this.lines.add(line);
        line.setPeriod(this);
        return this;
    }

    public Period removeLine(Line line) {
        this.lines.remove(line);
        line.setPeriod(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Period)) {
            return false;
        }
        return id != null && id.equals(((Period) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Period{" +
                "id=" + getId() +
                ", yearRef1=" + getYearRef1() +
                ", yearRef2=" + getYearRef2() +
                "}";
    }
}
