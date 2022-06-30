package ma.itroad.ram.kpi.service.dto;

import ma.itroad.ram.kpi.domain.Kpi;
import ma.itroad.ram.kpi.domain.Line;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link ma.itroad.ram.kpi.domain.Period} entity.
 */
public class PeriodDTO implements Serializable {

    private Long id;

    private String yearRef1;
    private String yearRef2;
    private List<Line> lines;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYearRef1() {
        return yearRef1;
    }

    public void setYearRef1(String yearRef1) {
        this.yearRef1 = yearRef1;
    }

    public String getYearRef2() {
        return yearRef2;
    }

    public void setYearRef2(String yearRef2) {
        this.yearRef2 = yearRef2;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PeriodDTO)) {
            return false;
        }

        PeriodDTO periodDTO = (PeriodDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, periodDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeriodDTO{" +
                "id=" + getId() +
                ", yearRef1=" + getYearRef1() +
                ", yearRef2=" + getYearRef2() +
                "}";
    }
}
