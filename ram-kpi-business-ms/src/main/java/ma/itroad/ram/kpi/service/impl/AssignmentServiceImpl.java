package ma.itroad.ram.kpi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ma.itroad.ram.kpi.domain.Assignment;
import ma.itroad.ram.kpi.service.OpenFeign.UserManagementService;
import ma.itroad.ram.kpi.repository.AssignmentRepository;
import ma.itroad.ram.kpi.service.AssignmentService;
import ma.itroad.ram.kpi.service.dto.AssignmentDTO;
import ma.itroad.ram.kpi.service.mapper.AssignmentMapper;

import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
//https://www.baeldung.com/spring-rest-template-list
import ma.itroad.ram.kpi.service.dto.UserDTO;
import ma.itroad.ram.kpi.service.dto.UserDTOResponse;

/**
 * Service Implementation for managing {@link Assignment}.
 */
@Service
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

    @Value("${users-management-url}")
    private String REQUEST_URI;

    private final Logger log = LoggerFactory.getLogger(AssignmentServiceImpl.class);

    private final AssignmentRepository assignmentRepository;

    private final AssignmentMapper assignmentMapper;

    private final UserManagementService userManagementService;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, AssignmentMapper assignmentMapper, UserManagementService userManagementService) {
        this.assignmentRepository = assignmentRepository;
        this.assignmentMapper = assignmentMapper;
        this.userManagementService = userManagementService;
    }

    @Override
    public AssignmentDTO save(AssignmentDTO assignmentDTO) {
        log.debug("Request to save Assignment : {}", assignmentDTO);
        Assignment assignment = assignmentMapper.toEntity(assignmentDTO);
        assignment = assignmentRepository.save(assignment);
        return assignmentMapper.toDto(assignment);
    }

    @Override
    public Optional<AssignmentDTO> partialUpdate(AssignmentDTO assignmentDTO) {
        log.debug("Request to partially update Assignment : {}", assignmentDTO);

        return assignmentRepository.findById(assignmentDTO.getId()).map(existingAssignment -> {
            assignmentMapper.partialUpdate(existingAssignment, assignmentDTO);

            return existingAssignment;
        }).map(assignmentRepository::save).map(assignmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AssignmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Assignments");
        return assignmentRepository.findAll(pageable).map(assignmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AssignmentDTO> findOne(Long id) {
        log.debug("Request to get Assignment : {}", id);
        return assignmentRepository.findById(id).map(assignmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Assignment : {}", id);
        assignmentRepository.deleteById(id);
    }

    @Override
    public List<AssignmentDTO> saveAll(List<AssignmentDTO> assignmentDTO) {
        List<Assignment> assignments = new ArrayList<>();

        for (AssignmentDTO dto : assignmentDTO) {
            Assignment assignment = assignmentMapper.toEntity(dto);
            assignments.add(assignment);
        }
        List<Assignment> result = assignmentRepository.saveAll(assignments);

        return result.stream().map(assignmentMapper::toDto).collect(Collectors.toList());
    }


    @Override
    public Optional<UserDTO> getUser(String id, String token) {
        String url = REQUEST_URI + id;
        System.out.println("url : " + url);
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

        ResponseEntity<UserDTO> response = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, UserDTO.class);
        System.out.println(response.getBody());
        return Optional.ofNullable(response.getBody());
    }


    @Override
    public List<UserDTO> getAllUsers() {

        List<UserDTO> users = new ArrayList<>();
        UserDTOResponse userDTOResponse = userManagementService.getAllUsers().getBody();
        System.out.println("userManagementService.getAllUsers() " + userDTOResponse.getUsers());
        users.forEach(System.out::println);

        return userDTOResponse.getUsers();
    }


    @Override
    @Transactional(readOnly = true)
    public Page<AssignmentDTO> findAllAssignments(Pageable pageable) {
        log.debug("Request to get all Assignments");
        return assignmentRepository.findAllAssignments(pageable).map(assignmentMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public List<String> findAllAssignedUsers() {
        log.debug("Request to get all Assignments");
        return assignmentRepository.findAllAssignedUsers();
    }



/*	@Override
	public List<AssignmentDTO> findByKpiGroupIdAndUserId(Long kpiGroupId, String userId) {
		log.debug("Request to findByKpiGroupIdAndUserId  : {}", kpiGroupId);
		List<Assignment> result= assignmentRepository.findByKpiGroupIdAndUserId(kpiGroupId, userId);
		log.debug("Request to findByKpiGroupIdAndUserId  result: {}", result);
		return result.stream().map(assignmentMapper::toDto).collect(Collectors.toList());

	}*/

    @Override
    public void deleteAssignments(Long kpiGroupId, String userId) {
        log.debug("Request to findByKpiGroupIdAndUserId  : {}", kpiGroupId);
        assignmentRepository.deleteByKpiGroupIdAndUserId(kpiGroupId, userId);

    }


    @Override
    @Transactional(readOnly = true)
    public List<AssignmentDTO> findByKpiGroupId(Long kpiGroupId) {
        log.debug("Request to get Assignment : {}", kpiGroupId);
        return assignmentRepository.findByKpiGroupId(kpiGroupId).stream().map(assignmentMapper::toDto).collect(Collectors.toList());
    }


}
