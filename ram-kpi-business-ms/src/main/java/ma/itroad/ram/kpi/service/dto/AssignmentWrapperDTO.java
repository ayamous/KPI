package ma.itroad.ram.kpi.service.dto;

import java.io.Serializable;
import java.util.List;

public class AssignmentWrapperDTO implements Serializable {

    private Long id;

    private String label;

    private List<UserDTO> users;


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

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "AssignmentWrapperDTO{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", users=" + users +
                '}';
    }
}
