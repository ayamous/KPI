package ma.itroad.ram.kpi.service.mapper;

import ma.itroad.ram.kpi.domain.Entite;
import ma.itroad.ram.kpi.service.dto.EntiteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Entite} and its DTO {@link EntiteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EntiteMapper extends EntityMapper<EntiteDTO, Entite> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EntiteDTO toDtoId(Entite entite);
}
