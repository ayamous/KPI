package ma.itroad.ram.kpi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A SystemSource.
 */
@Entity
@Table(name = "system_source")
public class SystemSource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "description")
    private String description;

   /* @OneToMany(mappedBy = "systemSource")
    @JsonIgnoreProperties(value = {"inductor", "kpiGroup", "entite", "category", "systemSource"}, allowSetters = true)
    private Set<Kpi> kpis = new HashSet<>();*/

    @ManyToMany(mappedBy = "systemSources")
    @JsonIgnoreProperties(value = {"systemSources"}, allowSetters = true)
    private Set<Kpi> kpis = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SystemSource id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public SystemSource label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return this.description;
    }

    public SystemSource description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Kpi> getKpis() {
        return this.kpis;
    }

    public void setKpis(Set<Kpi> kpis) {
        if (this.kpis != null) {
            this.kpis.forEach(i -> i.removeSystemSource(this));
        }
        if (kpis != null) {
            kpis.forEach(i -> i.addSystemSource(this));
        }
        this.kpis = kpis;
    }

    public SystemSource kpis(Set<Kpi> kpis) {
        this.setKpis(kpis);
        return this;
    }

    public SystemSource addKpi(Kpi kpi) {
        this.kpis.add(kpi);
        kpi.getSystemSources().add(this);
        return this;
    }

    public SystemSource removeKpi(Kpi kpi) {
        this.kpis.remove(kpi);
        kpi.getSystemSources().remove(this);
        return this;
    }

  /*  public Set<Kpi> getKpis() {
        return this.kpis;
    }

    public void setKpis(Set<Kpi> kpis) {
        if (this.kpis != null) {
            this.kpis.forEach(i -> i.setSystemSource(null));
        }
        if (kpis != null) {
            kpis.forEach(i -> i.setSystemSource(this));
        }
        this.kpis = kpis;
    }

    public SystemSource kpis(Set<Kpi> kpis) {
        this.setKpis(kpis);
        return this;
    }

    public SystemSource addKpi(Kpi kpi) {
        this.kpis.add(kpi);
        kpi.setSystemSource(this);
        return this;
    }

    public SystemSource removeKpi(Kpi kpi) {
        this.kpis.remove(kpi);
        kpi.setSystemSource(null);
        return this;
    }*/

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemSource)) {
            return false;
        }
        return id != null && id.equals(((SystemSource) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemSource{" +
                "id=" + getId() +
                ", label='" + getLabel() + "'" +
                ", description='" + getDescription() + "'" +
                "}";
    }
}
