package ma.itroad.ram.kpi.service.mapper;

import ma.itroad.ram.kpi.domain.ControlRule;
import ma.itroad.ram.kpi.service.dto.ControlRuleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ControlRule} and its DTO {@link ControlRuleDTO}.
 */
@Mapper(componentModel = "spring", uses = {KpiMapper.class})
public interface ControlRuleMapper extends EntityMapper<ControlRuleDTO, ControlRule> {
    // if we keep this, only the id will get mapped other fields are null
    // @Mapping(target = "kpiBase", source = "kpiBase", qualifiedByName = "id")
    //@Mapping(target = "kpiControl", source = "kpiControl", qualifiedByName = "id")
    ControlRuleDTO toDto(ControlRule s);
}
