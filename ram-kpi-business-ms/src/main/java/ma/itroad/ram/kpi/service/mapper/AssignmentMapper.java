package ma.itroad.ram.kpi.service.mapper;


import ma.itroad.ram.kpi.domain.Assignment;
import ma.itroad.ram.kpi.service.dto.AssignmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Assignment} and its DTO {@link AssignmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {KpiGroupMapper.class})
public interface AssignmentMapper extends EntityMapper<AssignmentDTO, Assignment> {
    @Mapping(target = "kpiGroup", source = "kpiGroup", qualifiedByName = "id")
    AssignmentDTO toDto(Assignment s);
}
