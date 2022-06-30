package ma.itroad.ram.kpi.service.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import ma.itroad.ram.kpi.domain.SystemSource;
import ma.itroad.ram.kpi.service.dto.SystemSourceDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-25T17:30:23+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.2 (Eclipse Adoptium)"
)
@Component
public class SystemSourceMapperImpl implements SystemSourceMapper {

    @Override
    public SystemSource toEntity(SystemSourceDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SystemSource systemSource = new SystemSource();

        systemSource.setId( dto.getId() );
        systemSource.setLabel( dto.getLabel() );
        systemSource.setDescription( dto.getDescription() );

        return systemSource;
    }

    @Override
    public SystemSourceDTO toDto(SystemSource entity) {
        if ( entity == null ) {
            return null;
        }

        SystemSourceDTO systemSourceDTO = new SystemSourceDTO();

        systemSourceDTO.setId( entity.getId() );
        systemSourceDTO.setLabel( entity.getLabel() );
        systemSourceDTO.setDescription( entity.getDescription() );

        return systemSourceDTO;
    }

    @Override
    public List<SystemSource> toEntity(List<SystemSourceDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<SystemSource> list = new ArrayList<SystemSource>( dtoList.size() );
        for ( SystemSourceDTO systemSourceDTO : dtoList ) {
            list.add( toEntity( systemSourceDTO ) );
        }

        return list;
    }

    @Override
    public List<SystemSourceDTO> toDto(List<SystemSource> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SystemSourceDTO> list = new ArrayList<SystemSourceDTO>( entityList.size() );
        for ( SystemSource systemSource : entityList ) {
            list.add( toDto( systemSource ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(SystemSource entity, SystemSourceDTO dto) {
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
    public Set<SystemSourceDTO> toDtoIdSet(Set<SystemSource> systemSource) {
        if ( systemSource == null ) {
            return null;
        }

        Set<SystemSourceDTO> set = new HashSet<SystemSourceDTO>( Math.max( (int) ( systemSource.size() / .75f ) + 1, 16 ) );
        for ( SystemSource systemSource1 : systemSource ) {
            set.add( toDto( systemSource1 ) );
        }

        return set;
    }
}
