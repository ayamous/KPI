package ma.itroad.ram.kpi.service.mapper;

import ma.itroad.ram.kpi.domain.KpiGroup;
import ma.itroad.ram.kpi.service.dto.KpiGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link KpiGroup} and its DTO {@link KpiGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface KpiGroupMapper extends EntityMapper<KpiGroupDTO, KpiGroup> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    KpiGroupDTO toDtoId(KpiGroup kpiGroup);
}
