package ma.itroad.ram.kpi.controller;


import ma.itroad.ram.kpi.exceptions.ResourceDeleteFailedException;
import ma.itroad.ram.kpi.model.*;
import ma.itroad.ram.kpi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin/management")
public class UserController {


    private final UserService userService;

    // KeyCloakService service;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // @RolesAllowed("user")
    @PostMapping("/users")
    private ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserVm userVm) {
        return ResponseEntity.ok().body(userService.createUser(userVm));
    }


    @GetMapping("/users")
    private ResponseEntity<UserDTOResponse> getAllUsers(@RequestParam(required = false) String search,
                                                        @Valid @RequestParam(required = false) Profile profile,
                                                        @Valid @RequestParam(required = false) Notification notification,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size,
                                                        @RequestParam(defaultValue = "label") String sortBy,
                                                        @RequestParam(defaultValue = "DESC") String sortDir,
                                                        @RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.println("Header Name: " + key + " Header Value: " + value);
        });
        UserDTOResponse list = userService.getAllUsers(search, profile, notification, page, size, sortBy, sortDir);
        System.out.println("List UserDTO : " + list);
        return ResponseEntity.ok().body(list);
    }


    @GetMapping("/users/{id}")
    private ResponseEntity<UserDTO> getUserById(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @DeleteMapping("/users/{id}")
    private ResponseEntity<Response> deleteUserById(@PathVariable("id") String id, HttpServletRequest request) {

        String token = "";
        final String authorizationHeaderValue = request.getHeader("Authorization");
        if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Bearer")) {
            token = authorizationHeaderValue.substring(7, authorizationHeaderValue.length());
        }
        List<String> usersIds = userService.getAllUsersIds(token);
        if (usersIds.contains(id)) {
            throw new ResourceDeleteFailedException("User is already affected to an kpi and can not be deleted");
        }
        Response response = userService.deleteUserById(id);
        return ResponseEntity.ok().body(response);
    }
   // @Valid

    @PutMapping("/users/{id}")
    private ResponseEntity UpdateUser(@PathVariable("id") String id, @Valid @RequestBody UserVm userVm) {
        System.out.println("Updating user with id : " + id);
        return ResponseEntity.ok().body(userService.updateUser(id, userVm));
    }

  /*  @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        System.out.println("initBinder called");
        webdataBinder.registerCustomEditor(UserVm.class, new UserVmConverter());
    }*/

/*    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {

        request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        AccessToken token = ((KeycloakPrincipal<?>) request.getUserPrincipal()).getKeycloakSecurityContext().getToken();

        String userId = token.getSubject();
        userService.logoutUser(userId);

        return new ResponseEntity<>("Hi!, you have logged out successfully!", HttpStatus.OK);

    }*/


/*
    @GetMapping(path = "/verification-link/{userId}")
    public String sendVerificationLink(@PathVariable("userId") String userId) {
        userService.sendVerificationLink(userId);
        return "Verification Link Send to Registered E-mail Id.";
    }
*/


}

