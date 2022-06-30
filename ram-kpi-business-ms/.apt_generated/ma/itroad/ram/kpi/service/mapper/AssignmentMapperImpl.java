package ma.itroad.ram.kpi.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import ma.itroad.ram.kpi.domain.Assignment;
import ma.itroad.ram.kpi.service.dto.AssignmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-25T17:30:23+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.2 (Eclipse Adoptium)"
)
@Component
public class AssignmentMapperImpl implements AssignmentMapper {

    @Autowired
    private KpiGroupMapper kpiGroupMapper;

    @Override
    public Assignment toEntity(AssignmentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Assignment assignment = new Assignment();

        assignment.setId( dto.getId() );
        assignment.setUserId( dto.getUserId() );
        assignment.kpiGroup( kpiGroupMapper.toEntity( dto.getKpiGroup() ) );

        return assignment;
    }

    @Override
    public List<Assignment> toEntity(List<AssignmentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Assignment> list = new ArrayList<Assignment>( dtoList.size() );
        for ( AssignmentDTO assignmentDTO : dtoList ) {
            list.add( toEntity( assignmentDTO ) );
        }

        return list;
    }

    @Override
    public List<AssignmentDTO> toDto(List<Assignment> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AssignmentDTO> list = new ArrayList<AssignmentDTO>( entityList.size() );
        for ( Assignment assignment : entityList ) {
            list.add( toDto( assignment ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Assignment entity, AssignmentDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getUserId() != null ) {
            entity.setUserId( dto.getUserId() );
        }
        if ( dto.getKpiGroup() != null ) {
            entity.kpiGroup( kpiGroupMapper.toEntity( dto.getKpiGroup() ) );
        }
    }

    @Override
    public AssignmentDTO toDto(Assignment s) {
        if ( s == null ) {
            return null;
        }

        AssignmentDTO assignmentDTO = new AssignmentDTO();

        assignmentDTO.setKpiGroup( kpiGroupMapper.toDtoId( s.getKpiGroup() ) );
        assignmentDTO.setId( s.getId() );
        assignmentDTO.setUserId( s.getUserId() );

        return assignmentDTO;
    }
}
