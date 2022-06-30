package ma.itroad.ram.kpi.service.mapper;

import ma.itroad.ram.kpi.domain.Document;
import ma.itroad.ram.kpi.service.dto.DocumentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Document} and its DTO {@link DocumentMapper}.
 */
@Mapper(componentModel = "spring", uses = {KpiMapper.class})
public interface DocumentMapper extends EntityMapper<DocumentDTO, Document> {
    //@Mapping(target = "kpi", source = "kpi", qualifiedByName = "id")
    DocumentDTO toDto(Document s);
}
