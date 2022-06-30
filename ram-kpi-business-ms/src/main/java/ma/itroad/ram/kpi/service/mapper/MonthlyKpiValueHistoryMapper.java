package ma.itroad.ram.kpi.service.mapper;

import ma.itroad.ram.kpi.domain.MonthlyKpiValueHistory;
import ma.itroad.ram.kpi.service.dto.MonthlyKpiValueHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MonthlyKpiValueHistory} and its DTO {@link MonthlyKpiValueHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = { MonthlyKpiValueMapper.class })
public interface MonthlyKpiValueHistoryMapper extends EntityMapper<MonthlyKpiValueHistoryDTO, MonthlyKpiValueHistory> {
    @Mapping(target = "monthlyKpiValue", source = "monthlyKpiValue", qualifiedByName = "id")
    MonthlyKpiValueHistoryDTO toDto(MonthlyKpiValueHistory s);
}

