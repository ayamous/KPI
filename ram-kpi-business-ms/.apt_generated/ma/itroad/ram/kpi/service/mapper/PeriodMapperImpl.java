package ma.itroad.ram.kpi.service.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import ma.itroad.ram.kpi.domain.Line;
import ma.itroad.ram.kpi.domain.Period;
import ma.itroad.ram.kpi.service.dto.PeriodDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-25T17:30:23+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.2 (Eclipse Adoptium)"
)
@Component
public class PeriodMapperImpl implements PeriodMapper {

    @Override
    public Period toEntity(PeriodDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Period period = new Period();

        period.setId( dto.getId() );
        period.setYearRef1( dto.getYearRef1() );
        period.setYearRef2( dto.getYearRef2() );
        Set<Line> set = dto.getLines();
        if ( set != null ) {
            period.lines( new HashSet<Line>( set ) );
        }

        return period;
    }

    @Override
    public PeriodDTO toDto(Period entity) {
        if ( entity == null ) {
            return null;
        }

        PeriodDTO periodDTO = new PeriodDTO();

        periodDTO.setId( entity.getId() );
        periodDTO.setYearRef1( entity.getYearRef1() );
        periodDTO.setYearRef2( entity.getYearRef2() );
        Set<Line> set = entity.getLines();
        if ( set != null ) {
            periodDTO.setLines( new HashSet<Line>( set ) );
        }

        return periodDTO;
    }

    @Override
    public List<Period> toEntity(List<PeriodDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Period> list = new ArrayList<Period>( dtoList.size() );
        for ( PeriodDTO periodDTO : dtoList ) {
            list.add( toEntity( periodDTO ) );
        }

        return list;
    }

    @Override
    public List<PeriodDTO> toDto(List<Period> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PeriodDTO> list = new ArrayList<PeriodDTO>( entityList.size() );
        for ( Period period : entityList ) {
            list.add( toDto( period ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Period entity, PeriodDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getYearRef1() != null ) {
            entity.setYearRef1( dto.getYearRef1() );
        }
        if ( dto.getYearRef2() != null ) {
            entity.setYearRef2( dto.getYearRef2() );
        }
        if ( entity.getLines() != null ) {
            Set<Line> set = dto.getLines();
            if ( set != null ) {
                entity.getLines().clear();
                entity.getLines().addAll( set );
            }
        }
        else {
            Set<Line> set = dto.getLines();
            if ( set != null ) {
                entity.lines( new HashSet<Line>( set ) );
            }
        }
    }

    @Override
    public PeriodDTO toDtoId(Period period) {
        if ( period == null ) {
            return null;
        }

        PeriodDTO periodDTO = new PeriodDTO();

        periodDTO.setId( period.getId() );

        return periodDTO;
    }
}
