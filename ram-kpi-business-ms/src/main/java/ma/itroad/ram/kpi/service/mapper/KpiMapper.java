package ma.itroad.ram.kpi.service.mapper;

import ma.itroad.ram.kpi.domain.Kpi;
import ma.itroad.ram.kpi.service.dto.KpiDTO;
import org.mapstruct.*;

import java.util.Set;


/**
 * Mapper for the entity {@link Kpi} and its DTO {@link KpiDTO}.
 */
@Mapper(componentModel = "spring", uses = {EntiteMapper.class, CategoryMapper.class, SystemSourceMapper.class, KpiGroupMapper.class, DocumentMapper.class, MonthlyKpiValueMapper.class})
public interface KpiMapper extends EntityMapper<KpiDTO, Kpi> {
    @Mapping(target = "inductor", source = "inductor", qualifiedByName = "id")
    @Mapping(target = "kpiGroup", source = "kpiGroup", qualifiedByName = "id")
    @Mapping(target = "documents", source = "documents")
    @Mapping(target = "monthlyKpiValues", source = "monthlyKpiValues")
    KpiDTO toDto(Kpi s);

    @Mapping(target = "removeSystemSource", ignore = true)
    Kpi toEntity(KpiDTO kpiDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    KpiDTO toDtoId(Kpi kpi);


}
