package ma.itroad.ram.kpi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A KpiGroup.
 */
@Entity
@Table(name = "kpi_group")
public class KpiGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "label")
    private String label;
    //cascade = CascadeType.MERGE,
    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"inductor", "kpiGroup", "entite", "category", "systemSource", "documents", "monthlyKpiValues"}, allowSetters = true)
    @JoinColumn(name = "kpi_group_id")
    private Set<Kpi> kpis = new HashSet<>();


    @OneToMany(mappedBy = "kpiGroup")
    @JsonIgnoreProperties(value = {"kpiGroup"}, allowSetters = true)
    private Set<Assignment> assignments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return this.id;
    }

    public KpiGroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public KpiGroup label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Kpi> getKpis() {
        return this.kpis;
    }

    public Set<Assignment> getAssignments() {
        return this.assignments;
    }

    public void setAssignments(Set<Assignment> assignments) {
        if (this.assignments != null) {
            this.assignments.forEach(i -> i.setKpiGroup(null));
        }
        if (assignments != null) {
            assignments.forEach(i -> i.setKpiGroup(this));
        }
        this.assignments = assignments;
    }

    public KpiGroup assignments(Set<Assignment> assignments) {
        this.setAssignments(assignments);
        return this;
    }

    public KpiGroup addAssignment(Assignment assignment) {
        this.assignments.add(assignment);
        assignment.setKpiGroup(this);
        return this;
    }

    public KpiGroup removeAssignment(Assignment assignment) {
        this.assignments.remove(assignment);
        assignment.setKpiGroup(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    public void setKpis(Set<Kpi> kpis) {
        this.kpis = kpis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KpiGroup)) {
            return false;
        }
        return id != null && id.equals(((KpiGroup) o).id);
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
        return "KpiGroup{" +
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }
}

// public void setKpis(Set<Kpi> kpis) {
// if (this.kpis != null) {
// this.kpis.forEach(i -> i.setKpiGroup(null));
// }
// if (kpis != null) {
// kpis.forEach(i -> i.setKpiGroup(this));
// }
// this.kpis = kpis;
// }

// public KpiGroup kpis(Set<Kpi> kpis) {
// this.setKpis(kpis);
// return this;
// }

// public KpiGroup addKpi(Kpi kpi) {
// this.kpis.add(kpi);
// kpi.setKpiGroup(this);
// return this;
// }

// public KpiGroup removeKpi(Kpi kpi) {
// this.kpis.remove(kpi);
// kpi.setKpiGroup(null);
// return this;
// }