package ma.itroad.ram.kpi.service.OpenFeign;

import ma.itroad.ram.kpi.service.dto.UserDTO;
import ma.itroad.ram.kpi.service.dto.UserDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "FeignUserManagement", url = "${users-management-url}")
public interface UserManagementService {

    @GetMapping("/{id}")
    ResponseEntity<UserDTO> getUserById(@PathVariable String id);

    @GetMapping
    ResponseEntity<UserDTOResponse> getAllUsers();

}
