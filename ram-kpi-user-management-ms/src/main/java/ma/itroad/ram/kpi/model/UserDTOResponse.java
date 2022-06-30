package ma.itroad.ram.kpi.model;


import java.util.List;

public class UserDTOResponse {
    private List<UserDTO> users;
    private Integer count;

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "UserDTOResponse{" +
                "users=" + users +
                ", count=" + count +
                '}';
    }
}
