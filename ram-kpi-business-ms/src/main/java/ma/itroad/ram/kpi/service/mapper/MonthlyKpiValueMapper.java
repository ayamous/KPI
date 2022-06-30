package ma.itroad.ram.kpi.service.mapper;

import ma.itroad.ram.kpi.domain.MonthlyKpiValue;
import ma.itroad.ram.kpi.service.dto.MonthlyKpiValueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MonthlyKpiValue} and its DTO {@link MonthlyKpiValueDTO}.
 */
@Mapper(componentModel = "spring", uses = {KpiMapper.class})
public interface MonthlyKpiValueMapper extends EntityMapper<MonthlyKpiValueDTO, MonthlyKpiValue> {
    @Mapping(target = "kpi.monthlyKpiValues", ignore = true)
    @Mapping(target = "kpi.kpiGroup", ignore = true)
    @Mapping(target = "kpi.systemSources", ignore = true)
    @Mapping(target = "kpi.documents", ignore = true)
    @Mapping(target = "kpi.category", ignore = true)
    @Mapping(target = "kpi.entite", ignore = true)
    @Mapping(target = "kpi.dirId", ignore = true)
    @Mapping(target = "kpi.description", ignore = true)
    @Mapping(target = "kpi.inductor", ignore = true)
    @Mapping(target = "kpi.estimationPeriodValue", ignore = true)
    @Mapping(target = "kpi.productionPeriod", ignore = true)
    @Mapping(target = "kpi.reminder", ignore = true)
    @Mapping(target = "kpi.estimationMethod", ignore = true)
    @Mapping(target = "kpi.isInductor", ignore = true)
    @Mapping(target = "kpi.isEstimable", ignore = true)
    @Mapping(target = "kpi.source", ignore = true)
    @Mapping(target = "kpi.estimationPeriod", ignore = true)
    @Mapping(target = "kpi.status", ignore = true)
    @Mapping(target = "kpi.perdiodicity", ignore = true)
    MonthlyKpiValueDTO toDto(MonthlyKpiValue s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MonthlyKpiValueDTO toDtoId(MonthlyKpiValue monthlyKpiValue);
}

