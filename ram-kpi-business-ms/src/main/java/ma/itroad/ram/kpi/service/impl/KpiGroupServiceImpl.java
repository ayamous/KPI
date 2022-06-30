package ma.itroad.ram.kpi.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import ma.itroad.ram.kpi.domain.Assignment;
import ma.itroad.ram.kpi.domain.Kpi;
import ma.itroad.ram.kpi.domain.KpiGroup;
import ma.itroad.ram.kpi.repository.KpiGroupRepository;
import ma.itroad.ram.kpi.service.AssignmentService;
import ma.itroad.ram.kpi.service.KpiGroupService;
import ma.itroad.ram.kpi.service.dto.AssignmentWrapperDTO;
import ma.itroad.ram.kpi.service.dto.KpiGroupDTO;
import ma.itroad.ram.kpi.service.dto.UserDTO;
import ma.itroad.ram.kpi.service.mapper.KpiGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KpiGroup}.
 */
@Service
@Transactional
public class KpiGroupServiceImpl implements KpiGroupService {

    private final Logger log = LoggerFactory.getLogger(KpiGroupServiceImpl.class);

    private final KpiGroupRepository kpiGroupRepository;

    private AssignmentService assignmentService;

    private final KpiGroupMapper kpiGroupMapper;

    public KpiGroupServiceImpl(KpiGroupRepository kpiGroupRepository, KpiGroupMapper kpiGroupMapper, AssignmentService assignmentService) {
        this.kpiGroupRepository = kpiGroupRepository;
        this.kpiGroupMapper = kpiGroupMapper;
        this.assignmentService = assignmentService;
    }

    @Override
    public KpiGroupDTO save(KpiGroupDTO kpiGroupDTO) {
        log.debug("Request to save KpiGroup : {}", kpiGroupDTO);
        KpiGroup kpiGroup = kpiGroupMapper.toEntity(kpiGroupDTO);
        kpiGroup = kpiGroupRepository.save(kpiGroup);
        return kpiGroupMapper.toDto(kpiGroup);
    }

    @Override
    public Optional<KpiGroupDTO> partialUpdate(KpiGroupDTO kpiGroupDTO) {
        log.debug("Request to partially update KpiGroup : {}", kpiGroupDTO);

        return kpiGroupRepository
                .findById(kpiGroupDTO.getId())
                .map(existingKpiGroup -> {
                    kpiGroupMapper.partialUpdate(existingKpiGroup, kpiGroupDTO);

                    return existingKpiGroup;
                })
                .map(kpiGroupRepository::save)
                .map(kpiGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KpiGroupDTO> findAll(String label, Pageable pageable) {
        log.debug("Request to get all KpiGroups");
        if (label == null) {
            return kpiGroupRepository.findAll(pageable).map(kpiGroupMapper::toDto);
        } else {
            return kpiGroupRepository.findByLabelContainingIgnoreCase(label, pageable).map(kpiGroupMapper::toDto);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<KpiGroupDTO> findOne(Long id) {
        log.debug("Request to get KpiGroup : {}", id);
        return kpiGroupRepository.findById(id).map(kpiGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KpiGroup : {}", id);
        kpiGroupRepository.deleteById(id);
    }

    @Override
    public Page<KpiGroupDTO> findAllJoinAssignments(Pageable pageable) {
        log.debug("Request to findAll KpiGroup : {}", kpiGroupRepository.findAllJoinAssignments(pageable));
        return kpiGroupRepository.findAllJoinAssignments(pageable).map(kpiGroupMapper::toDto);
    }


    @Override
    public List<AssignmentWrapperDTO> addUsersDetails(Page<KpiGroupDTO> kpiGroups, String token) {
        log.debug("Request to delete KpiGroup : {}", kpiGroups);

        List<KpiGroupDTO> KpiGroupDTOItems = kpiGroups.getContent();

        List<UserDTO> users = assignmentService.getAllUsers();

        List<AssignmentWrapperDTO> assignmentWrapperDtoList = new ArrayList<>();
        for (KpiGroupDTO kpiGroupDTOItem : KpiGroupDTOItems) {
            AssignmentWrapperDTO assignmentWrapperDto = new AssignmentWrapperDTO();

            KpiGroupDTO kpgDTo = kpiGroupDTOItem;
            if (kpgDTo != null) {
                assignmentWrapperDto.setId(kpgDTo.getId());
                assignmentWrapperDto.setLabel(kpgDTo.getLabel());
                List<UserDTO> assignedUsers = getUserDTOList(kpgDTo.getAssignments(), users);
                assignmentWrapperDto.setUsers(assignedUsers);
            }
            assignmentWrapperDtoList.add(assignmentWrapperDto);

        }

        /*Page<AssignmentWrapperDTO> page = new PageImpl<>(assignmentWrapperDtoList,
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()),
                assignmentWrapperDtoList.size());*/

        return assignmentWrapperDtoList;

    }

    private List<UserDTO> getUserDTOList(Set<Assignment> assignments, List<UserDTO> users) {

        List<UserDTO> usersDTOList = new ArrayList<>();

        if (assignments != null && assignments.size() >= 1) {
            List<String> userIds = assignments.stream().map(assignment -> assignment.getUserId()).collect(Collectors.toList());
            Iterator<String> it = userIds.iterator();
            while (it.hasNext()) {
                String userId = it.next();
                UserDTO user = findByUserId(userId, users);
                usersDTOList.add(user);
            }
        }

        return usersDTOList;
    }

    public UserDTO findByUserId(
            String userId, List<UserDTO> users) {
        for (UserDTO user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    private KpiGroupDTO mapToDTO(KpiGroup kpiGroup) {

        KpiGroupDTO kpiGroupDto = new KpiGroupDTO();
        kpiGroupDto.setId(kpiGroup.getId());
        kpiGroupDto.setLabel(kpiGroup.getLabel());

        Set<Kpi> kpis = new HashSet<>();

        for (Kpi kpi : kpiGroup.getKpis()) {
            Kpi newKpi = new Kpi();
            newKpi.setId(kpi.getId());
            newKpi.setReference(kpi.getReference());
            newKpi.setName(kpi.getName());
            kpis.add(newKpi);
        }
        kpiGroupDto.setKpis(kpis);

        return kpiGroupDto;
    }

}
