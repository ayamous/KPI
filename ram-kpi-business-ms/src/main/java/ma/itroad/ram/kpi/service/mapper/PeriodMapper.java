package ma.itroad.ram.kpi.service.mapper;

import ma.itroad.ram.kpi.domain.Period;
import ma.itroad.ram.kpi.service.dto.PeriodDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Period} and its DTO {@link PeriodDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PeriodMapper extends EntityMapper<PeriodDTO, Period> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PeriodDTO toDtoId(Period period);
}
