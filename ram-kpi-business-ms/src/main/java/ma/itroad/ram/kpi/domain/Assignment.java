package ma.itroad.ram.kpi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Task entity.\n@author The JHipster team.
 */
@Entity
@Table(name = "assignment")
public class Assignment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId;


    //@OnDelete(action = OnDeleteAction.CASCADE)

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"assignments", "kpis", "kpiGroup"}, allowSetters = true)
    @JoinColumn(name = "kpi_group_id", nullable = false)
    private KpiGroup kpiGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Assignment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public Assignment userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public KpiGroup getKpiGroup() {
        return this.kpiGroup;
    }

    public void setKpiGroup(KpiGroup kpiGroup) {
        this.kpiGroup = kpiGroup;
    }

    public Assignment kpiGroup(KpiGroup kpiGroup) {
        this.setKpiGroup(kpiGroup);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Assignment)) {
            return false;
        }
        return id != null && id.equals(((Assignment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + getId() +
                ", userId='" + getUserId() + "'" +
                "}";
    }
}
