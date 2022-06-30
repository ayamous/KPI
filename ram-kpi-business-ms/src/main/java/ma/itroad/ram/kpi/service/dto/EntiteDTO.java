package ma.itroad.ram.kpi.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ma.itroad.ram.kpi.domain.Entite} entity.
 */
public class EntiteDTO implements Serializable {

    private Long id;

    private String label;

    private String description;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntiteDTO)) {
            return false;
        }

        EntiteDTO entiteDTO = (EntiteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, entiteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EntiteDTO{" +
                "id=" + getId() +
                ", label='" + getLabel() + "'" +
                ", description='" + getDescription() + "'" +
                "}";
    }
}
