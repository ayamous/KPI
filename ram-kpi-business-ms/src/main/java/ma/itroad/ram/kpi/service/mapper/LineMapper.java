package ma.itroad.ram.kpi.service.mapper;

import ma.itroad.ram.kpi.domain.Line;
import ma.itroad.ram.kpi.service.dto.LineDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Line} and its DTO {@link LineDTO}.
 */
@Mapper(componentModel = "spring", uses = {PeriodMapper.class})
public interface LineMapper extends EntityMapper<LineDTO, Line> {
    @Mapping(target = "period", source = "period", qualifiedByName = "id")
    LineDTO toDto(Line s);
}
