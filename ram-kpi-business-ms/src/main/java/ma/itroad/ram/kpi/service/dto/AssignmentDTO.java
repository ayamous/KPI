package ma.itroad.ram.kpi.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ma.itroad.ram.kpi.domain.Assignment} entity.
 */
@Schema(description = "Assignment entity..")
public class AssignmentDTO implements Serializable {

    private Long id;

    private String userId;

    private KpiGroupDTO kpiGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public KpiGroupDTO getKpiGroup() {
        return kpiGroup;
    }

    public void setKpiGroup(KpiGroupDTO kpiGroup) {
        this.kpiGroup = kpiGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssignmentDTO)) {
            return false;
        }

        AssignmentDTO assignmentDTO = (AssignmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, assignmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssignmentDTO{" +
                "id=" + getId() +
                ", userId='" + getUserId() + "'" +
                ", kpiGroup=" + getKpiGroup() +
                "}";
    }
}
