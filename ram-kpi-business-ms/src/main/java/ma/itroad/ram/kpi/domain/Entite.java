package ma.itroad.ram.kpi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Entite.
 */
@Entity
@Table(name = "entite")
public class Entite implements Serializable {

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


    @OneToMany(mappedBy = "entite")
    @JsonIgnoreProperties(value = {"inductor", "kpiGroup", "entite", "category", "systemSource"}, allowSetters = true)
    private Set<Kpi> kpis = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Entite id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public Entite label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return this.description;
    }

    public Entite description(String description) {
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
            this.kpis.forEach(i -> i.setEntite(null));
        }
        if (kpis != null) {
            kpis.forEach(i -> i.setEntite(this));
        }
        this.kpis = kpis;
    }

    public Entite kpis(Set<Kpi> kpis) {
        this.setKpis(kpis);
        return this;
    }

    public Entite addKpi(Kpi kpi) {
        this.kpis.add(kpi);
        kpi.setEntite(this);
        return this;
    }

    public Entite removeKpi(Kpi kpi) {
        this.kpis.remove(kpi);
        kpi.setEntite(null);
        return this;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entite)) {
            return false;
        }
        return id != null && id.equals(((Entite) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entite{" +
                "id=" + getId() +
                ", label='" + getLabel() + "'" +
                ", description='" + getDescription() + "'" +
                "}";
    }
}
