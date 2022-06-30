package ma.itroad.ram.kpi.service.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import ma.itroad.ram.kpi.domain.Assignment;
import ma.itroad.ram.kpi.domain.Kpi;
import ma.itroad.ram.kpi.domain.KpiGroup;
import ma.itroad.ram.kpi.service.dto.KpiGroupDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-25T17:30:24+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.2 (Eclipse Adoptium)"
)
@Component
public class KpiGroupMapperImpl implements KpiGroupMapper {

    @Override
    public KpiGroup toEntity(KpiGroupDTO dto) {
        if ( dto == null ) {
            return null;
        }

        KpiGroup kpiGroup = new KpiGroup();

        kpiGroup.setId( dto.getId() );
        kpiGroup.setLabel( dto.getLabel() );
        Set<Assignment> set = dto.getAssignments();
        if ( set != null ) {
            kpiGroup.assignments( new HashSet<Assignment>( set ) );
        }
        Set<Kpi> set1 = dto.getKpis();
        if ( set1 != null ) {
            kpiGroup.setKpis( new HashSet<Kpi>( set1 ) );
        }

        return kpiGroup;
    }

    @Override
    public KpiGroupDTO toDto(KpiGroup entity) {
        if ( entity == null ) {
            return null;
        }

        KpiGroupDTO kpiGroupDTO = new KpiGroupDTO();

        kpiGroupDTO.setId( entity.getId() );
        kpiGroupDTO.setLabel( entity.getLabel() );
        Set<Kpi> set = entity.getKpis();
        if ( set != null ) {
            kpiGroupDTO.setKpis( new HashSet<Kpi>( set ) );
        }
        Set<Assignment> set1 = entity.getAssignments();
        if ( set1 != null ) {
            kpiGroupDTO.setAssignments( new HashSet<Assignment>( set1 ) );
        }

        return kpiGroupDTO;
    }

    @Override
    public List<KpiGroup> toEntity(List<KpiGroupDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<KpiGroup> list = new ArrayList<KpiGroup>( dtoList.size() );
        for ( KpiGroupDTO kpiGroupDTO : dtoList ) {
            list.add( toEntity( kpiGroupDTO ) );
        }

        return list;
    }

    @Override
    public List<KpiGroupDTO> toDto(List<KpiGroup> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<KpiGroupDTO> list = new ArrayList<KpiGroupDTO>( entityList.size() );
        for ( KpiGroup kpiGroup : entityList ) {
            list.add( toDto( kpiGroup ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(KpiGroup entity, KpiGroupDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getLabel() != null ) {
            entity.setLabel( dto.getLabel() );
        }
        if ( entity.getAssignments() != null ) {
            Set<Assignment> set = dto.getAssignments();
            if ( set != null ) {
                entity.getAssignments().clear();
                entity.getAssignments().addAll( set );
            }
        }
        else {
            Set<Assignment> set = dto.getAssignments();
            if ( set != null ) {
                entity.assignments( new HashSet<Assignment>( set ) );
            }
        }
        if ( entity.getKpis() != null ) {
            Set<Kpi> set1 = dto.getKpis();
            if ( set1 != null ) {
                entity.getKpis().clear();
                entity.getKpis().addAll( set1 );
            }
        }
        else {
            Set<Kpi> set1 = dto.getKpis();
            if ( set1 != null ) {
                entity.setKpis( new HashSet<Kpi>( set1 ) );
            }
        }
    }

    @Override
    public KpiGroupDTO toDtoId(KpiGroup kpiGroup) {
        if ( kpiGroup == null ) {
            return null;
        }

        KpiGroupDTO kpiGroupDTO = new KpiGroupDTO();

        kpiGroupDTO.setId( kpiGroup.getId() );

        return kpiGroupDTO;
    }
}
