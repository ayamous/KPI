package ma.itroad.ram.kpi.service.dto;

import ma.itroad.ram.kpi.domain.Assignment;
import ma.itroad.ram.kpi.domain.Kpi;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link ma.itroad.ram.kpi.domain.KpiGroup} entity.
 */
public class KpiGroupDTO implements Serializable {

    private Long id;

    private String label;

    private Set<Kpi> kpis;

    private Set<Assignment> assignments;

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

    public Set<Kpi> getKpis() {
        return kpis;
    }

    public void setKpis(Set<Kpi> kpis) {
        this.kpis = kpis;
    }

    public Set<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<Assignment> assignments) {
        this.assignments = assignments;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KpiGroupDTO)) {
            return false;
        }

        KpiGroupDTO kpiGroupDTO = (KpiGroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, kpiGroupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "KpiGroupDTO{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", kpis=" + kpis +
                ", assignments=" + assignments +
                '}';
    }
}
