package ma.itroad.ram.kpi.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import ma.itroad.ram.kpi.domain.Entite;
import ma.itroad.ram.kpi.service.dto.EntiteDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-25T17:30:23+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.2 (Eclipse Adoptium)"
)
@Component
public class EntiteMapperImpl implements EntiteMapper {

    @Override
    public Entite toEntity(EntiteDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Entite entite = new Entite();

        entite.setId( dto.getId() );
        entite.setLabel( dto.getLabel() );
        entite.setDescription( dto.getDescription() );

        return entite;
    }

    @Override
    public EntiteDTO toDto(Entite entity) {
        if ( entity == null ) {
            return null;
        }

        EntiteDTO entiteDTO = new EntiteDTO();

        entiteDTO.setId( entity.getId() );
        entiteDTO.setLabel( entity.getLabel() );
        entiteDTO.setDescription( entity.getDescription() );

        return entiteDTO;
    }

    @Override
    public List<Entite> toEntity(List<EntiteDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Entite> list = new ArrayList<Entite>( dtoList.size() );
        for ( EntiteDTO entiteDTO : dtoList ) {
            list.add( toEntity( entiteDTO ) );
        }

        return list;
    }

    @Override
    public List<EntiteDTO> toDto(List<Entite> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<EntiteDTO> list = new ArrayList<EntiteDTO>( entityList.size() );
        for ( Entite entite : entityList ) {
            list.add( toDto( entite ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Entite entity, EntiteDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getLabel() != null ) {
            entity.setLabel( dto.getLabel() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
    }

    @Override
    public EntiteDTO toDtoId(Entite entite) {
        if ( entite == null ) {
            return null;
        }

        EntiteDTO entiteDTO = new EntiteDTO();

        entiteDTO.setId( entite.getId() );

        return entiteDTO;
    }
}
