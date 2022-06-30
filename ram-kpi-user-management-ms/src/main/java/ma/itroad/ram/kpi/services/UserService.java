package ma.itroad.ram.kpi.services;


import ma.itroad.ram.kpi.model.*;

import javax.ws.rs.core.Response;
import java.util.List;

public interface UserService {

    UserDTO createUser(UserVm userVm);
    UserDTOResponse getAllUsers(String search, Profile profile, Notification notification, int page, int size, String sortBy, String sortDir);
    UserDTO getUserById(String id);
    Response deleteUserById(String id);
    UserDTO updateUser(String id, UserVm userVm);
    List<String> getAllUsersIds(String token);
    //List<String> getAllRoles();


}
