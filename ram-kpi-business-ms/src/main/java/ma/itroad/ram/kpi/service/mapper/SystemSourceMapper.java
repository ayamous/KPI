package ma.itroad.ram.kpi.service.mapper;

import java.util.Set;

import ma.itroad.ram.kpi.domain.SystemSource;
import ma.itroad.ram.kpi.service.dto.SystemSourceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SystemSource} and its DTO {@link SystemSourceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SystemSourceMapper extends EntityMapper<SystemSourceDTO, SystemSource> {
    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<SystemSourceDTO> toDtoIdSet(Set<SystemSource> systemSource);
}
