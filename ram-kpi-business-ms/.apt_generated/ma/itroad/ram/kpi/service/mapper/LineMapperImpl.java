package ma.itroad.ram.kpi.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import ma.itroad.ram.kpi.domain.Line;
import ma.itroad.ram.kpi.service.dto.LineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-25T17:30:24+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.2 (Eclipse Adoptium)"
)
@Component
public class LineMapperImpl implements LineMapper {

    @Autowired
    private PeriodMapper periodMapper;

    @Override
    public Line toEntity(LineDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Line line = new Line();

        line.setId( dto.getId() );
        line.setVersion( dto.getVersion() );
        line.setFrom( dto.getFrom() );
        line.setTo( dto.getTo() );
        line.setStatus( dto.getStatus() );
        line.period( periodMapper.toEntity( dto.getPeriod() ) );

        return line;
    }

    @Override
    public List<Line> toEntity(List<LineDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Line> list = new ArrayList<Line>( dtoList.size() );
        for ( LineDTO lineDTO : dtoList ) {
            list.add( toEntity( lineDTO ) );
        }

        return list;
    }

    @Override
    public List<LineDTO> toDto(List<Line> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<LineDTO> list = new ArrayList<LineDTO>( entityList.size() );
        for ( Line line : entityList ) {
            list.add( toDto( line ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Line entity, LineDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getVersion() != null ) {
            entity.setVersion( dto.getVersion() );
        }
        if ( dto.getFrom() != null ) {
            entity.setFrom( dto.getFrom() );
        }
        if ( dto.getTo() != null ) {
            entity.setTo( dto.getTo() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( dto.getPeriod() != null ) {
            entity.period( periodMapper.toEntity( dto.getPeriod() ) );
        }
    }

    @Override
    public LineDTO toDto(Line s) {
        if ( s == null ) {
            return null;
        }

        LineDTO lineDTO = new LineDTO();

        lineDTO.setPeriod( periodMapper.toDtoId( s.getPeriod() ) );
        lineDTO.setId( s.getId() );
        lineDTO.setVersion( s.getVersion() );
        lineDTO.setFrom( s.getFrom() );
        lineDTO.setTo( s.getTo() );
        lineDTO.setStatus( s.getStatus() );

        return lineDTO;
    }
}
