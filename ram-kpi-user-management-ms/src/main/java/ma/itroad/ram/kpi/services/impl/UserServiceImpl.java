package ma.itroad.ram.kpi.services.impl;

import ma.itroad.ram.kpi.exceptions.ResourceAlreadyExistException;
import ma.itroad.ram.kpi.exceptions.ResourceNotFoundException;
import ma.itroad.ram.kpi.mapper.UserMapper;
import ma.itroad.ram.kpi.model.*;
import ma.itroad.ram.kpi.services.UserService;
import ma.itroad.ram.kpi.services.config.KeycloakClientService;
import ma.itroad.ram.kpi.services.executor.ActionsEmailExecutor;
import ma.itroad.ram.kpi.services.executor.VerificationLinkExecutor;
import ma.itroad.ram.kpi.utils.RandomsUtils;
import org.apache.http.impl.client.HttpClients;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Value("${redirect-uri}")
    private String redirectUri;

    @Value("${users-assignment-uri}")
    private String usersAssignmentUri;

    @Value("${keycloak.resource}")
    private String clientId;

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private UserMapper userMapper;

    private KeycloakClientService keycloakClientService;

    private VerificationLinkExecutor verificationLinkExecutor;

    private ActionsEmailExecutor actionsEmailExecutor;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, KeycloakClientService keycloakClientService, VerificationLinkExecutor verificationLinkExecutor, ActionsEmailExecutor actionsEmailExecutor) {
        this.userMapper = userMapper;
        this.keycloakClientService = keycloakClientService;
        this.verificationLinkExecutor = verificationLinkExecutor;
        this.actionsEmailExecutor = actionsEmailExecutor;
    }

    // Create User
    public UserDTO createUser(UserVm userVm) {

        if (Boolean.TRUE.equals(existsByEmail(userVm.getEmail()))) {
            throw new ResourceAlreadyExistException("Email is already taken : " + userVm.getEmail());
        }

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userVm.getEmail());
        user.setFirstName(userVm.getFirstName());
        user.setLastName(userVm.getLastName());
        user.setEmail(userVm.getEmail());
        user.setEmailVerified(false);
        user.setAttributes(userVm.getAttributes());

        // Get realm
        RealmResource realmResource = keycloakClientService.getRealmResource();

        System.out.printf("RealmResource-------- : %s%n", realmResource);

        UsersResource usersResource = realmResource.users();
        // Create user (requires manage-users role)
        Response response = usersResource.create(user);

        System.out.printf("Response: %s %s %s%n", response.getStatus(), response.getStatusInfo(), response.getLocation());

        String userId = CreatedResponseUtil.getCreatedId(response);
        System.out.printf("User successfully registered with Id: %s%n", userId);

        // Define password credential
        String temporaryPassword = RandomsUtils.randomPassword();
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(true);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(temporaryPassword);

        UserResource userResource = usersResource.get(userId);
        // Set password credential
        userResource.resetPassword(passwordCred);

        // Get realm role "tester" (requires view-realm role)
        RoleRepresentation roleRepresentation = realmResource.roles()
                .get(userVm.getProfile().toString()).toRepresentation();
        System.out.printf("User role ----created with userId: %s%n", roleRepresentation);

        // Assign realm role tester to user
        userResource.roles().realmLevel()
                .add(Collections.singletonList(roleRepresentation));

        UserDTO userDTO = userMapper.toDto(user);
        if (userDTO.getId() == null)
            userDTO.setId(userId);

        List<String> profiles = userDTO.getProfile();
        if (profiles == null)
            profiles = new ArrayList<>();

        profiles.add(userVm.getProfile().toString());
        userDTO.setProfile(profiles);
        // Send password reset E-Mail
        actionsEmailExecutor.send(usersResource, userId);
        //verificationLinkExecutor.send(usersResource, userId);
        System.out.printf("returning userDTO : %s%n", userDTO);

        return userDTO;
    }

    // Read Users
    public UserDTOResponse getAllUsers(String search, Profile profile, Notification notification, int page, int size, String sortBy, String sortDir) {
        //(@QueryParam("username") String var1, @QueryParam("firstName") String var2, @QueryParam("lastName") String var3, @QueryParam("email") String var4, @QueryParam("first") Integer var5, @QueryParam("max") Integer var6)
        List<UserRepresentation> list = keycloakClientService.getRealmResource().users().search(search, page, size);
        Integer count = keycloakClientService.getRealmResource().users().count(search);
        System.out.println("count .." + count);
        list.forEach((user) -> log.info("user attribute {}", user.getAttributes()));
        List<UserDTO> usersDTO = list.stream().map(userMapper::toDto).collect(Collectors.toList());

        usersDTO = usersDTO.stream()
                .peek(item -> item.setProfile(userRealmRoles(item.getId())))
                .filter(item -> item.getProfile().size() > 0)
                .filter(item -> profile == null || "".equals(profile) || item.getProfile().contains(profile.name()))
                .filter(item -> notification == null || "".equals(notification) || item.getAttributes() != null && item.getAttributes().containsKey("notification") && item.getAttributes().containsValue(Collections.singletonList(notification.name())))
                .collect(Collectors.toList());

        UserDTOResponse response = new UserDTOResponse();
        response.setUsers(usersDTO);
        response.setCount(count);
        return response;
    }

    // Get User realmRoles
    public List<String> userRealmRoles(String id) {
        List<String> roles = new ArrayList<>();
        List<RoleRepresentation> roleRepresentations = keycloakClientService.getRealmResource().users().get(id)
                .roles().getAll()
                .getRealmMappings();

        if (roleRepresentations != null)
            roleRepresentations.forEach(x -> {
                if (hasProfile(x.getName()))
                    roles.add(x.getName());
            });

        return roles;
    }

    public static boolean hasProfile(String test) {
        for (Profile c : Profile.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }
        return false;
    }

    // @Async("threadPoolTaskExecutor1")
    public void executeActionsEmail(UsersResource usersResource, String userId) {
        // try {
        // } catch (ClientErrorException e) {
        //     e.printStackTrace();
        // }
        usersResource.get(userId).executeActionsEmail(clientId, redirectUri, Arrays.asList("UPDATE_PASSWORD"));

        System.out.println("executeActionsEmail success ");


    }


    // Read User by id
    public UserDTO getUserById(String id) {
        UserRepresentation user = keycloakClientService.getRealmResource()
                .users().get(id).toRepresentation();
        if (user == null)
            throw new ResourceNotFoundException(String.format("user with ID : %s , not found", id));
        UserDTO userDTO = userMapper.toDto(user);
        userDTO.setProfile(userRealmRoles(user.getId()));
        return userDTO;
    }

    public Response deleteUserById(String userId) {
        RealmResource realmResource = keycloakClientService.getRealmResource();

        return realmResource.users().delete(userId);
    }

    public UserDTO updateUser(String userId, UserVm userVm) {

        // Get realm
        RealmResource realmResource = keycloakClientService.getRealmResource();

        System.out.printf("RealmResource-------- : %s%n", realmResource);

        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(userId);
        UserRepresentation userRep = new UserRepresentation();
        // userRep.setUsername(userVm.);
        userRep.setFirstName(userVm.getFirstName());
        userRep.setLastName(userVm.getLastName());
        userRep.setEmail(userVm.getEmail());
        userRep.setEnabled(true);
        userRep.setAttributes(userVm.getAttributes());
        // userRep.setClientRoles();
        // Get realm role "tester" (requires view-realm role)
        RoleRepresentation roleRepresentation = keycloakClientService.getRealmResource().roles()
                .get(userVm.getProfile().toString()).toRepresentation();
        System.out.printf("User role ----created with userId: %s%n", roleRepresentation);


        // List<RoleRepresentation> rolesToRemove = Arrays.toString(Profile.values());

        List<String> list = Arrays.asList("saisie", "consultant", "admin");

        System.out.printf("roleNames..: %s%n", list);


        //String realmName = realmResource.toRepresentation().getRealm();

        List<RoleRepresentation> roleRepresentationsToRemove = new ArrayList<>();
        for (String roleName : list) {
            System.out.println("roleName..: " + roleName);
                if (!roleName.equals(userVm.getProfile().toString())) {
                    if(realmResource.roles().get(roleName) != null){
                        RoleRepresentation role = realmResource.roles().get(roleName).toRepresentation();
                        System.out.printf("role..: %s%n", role);
                        roleRepresentationsToRemove.add(role);
                    }

                }
        }
        System.out.println("roleRepresentations : " + roleRepresentationsToRemove);

        // Assign realm role tester to user
        userResource.roles().realmLevel()
                .remove(roleRepresentationsToRemove);

        userResource.roles().realmLevel()
                .add(Collections.singletonList(roleRepresentation));

        usersResource.get(userId).update(userRep);

        UserDTO userDTO = userMapper.toDto(userRep);
        userDTO.setId(userId);
        List<String> profiles = userDTO.getProfile();

        if (profiles == null)
            profiles = new ArrayList<>();

        profiles.add(userVm.getProfile().toString());
        userDTO.setProfile(profiles);


        return userDTO;

    }


    // check user already exists
    public boolean existsByEmail(String email) {

        // Get realm
        RealmResource realmResource = keycloakClientService.getRealmResource();
        System.out.printf("RealmResource-------- : %s%n", realmResource);
        UsersResource usersResource = realmResource.users();
        List<UserRepresentation> existingUsersByUsername = usersResource.search(email, 0, 1);
        log.debug("User already exists because result list is not empty (size is: {})",
                (existingUsersByUsername != null ? existingUsersByUsername.size() : "null"));
        return (existingUsersByUsername != null && !existingUsersByUsername.isEmpty());
    }


    public List<String> getAllRoles() {
        ClientRepresentation clientRep = keycloakClientService.getRealmResource()
                .clients()
                .findByClientId(clientId)
                .get(0);

        List<RoleRepresentation> roles = keycloakClientService
                .getRealmResource()
                .clients()
                .get(clientRep.getId())
                .roles()
                .list();

        log.debug("--------Roles-------" + roles);

        return keycloakClientService
                .getRealmResource()
                .clients()
                .get(clientRep.getId())
                .roles()
                .list()
                .stream()
                .map(role -> role.getName())
                .collect(Collectors.toList());
    }


    @Override
    public List<String> getAllUsersIds(String token) {

        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `accept` header
        // set custom header
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        // build the request
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<String>> response =
                restTemplate.exchange(
                        usersAssignmentUri,
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<List<String>>() {
                        });

        List<String> usersIds = response.getBody();

        assert usersIds != null;
        usersIds.forEach(System.out::println);

        return usersIds;
    }


}


