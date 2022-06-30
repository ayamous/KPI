package ma.itroad.ram.kpi.service.mapper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import ma.itroad.ram.kpi.domain.Kpi;
import ma.itroad.ram.kpi.domain.MonthlyKpiValue;
import ma.itroad.ram.kpi.service.dto.KpiDTO;
import ma.itroad.ram.kpi.service.dto.MonthlyKpiValueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-25T17:30:24+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.2 (Eclipse Adoptium)"
)
@Component
public class MonthlyKpiValueMapperImpl implements MonthlyKpiValueMapper {

    @Autowired
    private KpiMapper kpiMapper;

    @Override
    public MonthlyKpiValue toEntity(MonthlyKpiValueDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MonthlyKpiValue monthlyKpiValue = new MonthlyKpiValue();

        monthlyKpiValue.setId( dto.getId() );
        monthlyKpiValue.setLabel( dto.getLabel() );
        monthlyKpiValue.setDate( dto.getDate() );
        monthlyKpiValue.setValue( dto.getValue() );
        monthlyKpiValue.setType( dto.getType() );
        monthlyKpiValue.setStatus( dto.getStatus() );
        monthlyKpiValue.kpi( kpiMapper.toEntity( dto.getKpi() ) );
        monthlyKpiValue.setMethod( dto.getMethod() );

        return monthlyKpiValue;
    }

    @Override
    public List<MonthlyKpiValue> toEntity(List<MonthlyKpiValueDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<MonthlyKpiValue> list = new ArrayList<MonthlyKpiValue>( dtoList.size() );
        for ( MonthlyKpiValueDTO monthlyKpiValueDTO : dtoList ) {
            list.add( toEntity( monthlyKpiValueDTO ) );
        }

        return list;
    }

    @Override
    public List<MonthlyKpiValueDTO> toDto(List<MonthlyKpiValue> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MonthlyKpiValueDTO> list = new ArrayList<MonthlyKpiValueDTO>( entityList.size() );
        for ( MonthlyKpiValue monthlyKpiValue : entityList ) {
            list.add( toDto( monthlyKpiValue ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(MonthlyKpiValue entity, MonthlyKpiValueDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getLabel() != null ) {
            entity.setLabel( dto.getLabel() );
        }
        if ( dto.getDate() != null ) {
            entity.setDate( dto.getDate() );
        }
        if ( dto.getValue() != null ) {
            entity.setValue( dto.getValue() );
        }
        if ( dto.getType() != null ) {
            entity.setType( dto.getType() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( dto.getKpi() != null ) {
            entity.kpi( kpiMapper.toEntity( dto.getKpi() ) );
        }
        if ( dto.getMethod() != null ) {
            entity.setMethod( dto.getMethod() );
        }
    }

    @Override
    public MonthlyKpiValueDTO toDto(MonthlyKpiValue s) {
        if ( s == null ) {
            return null;
        }

        MonthlyKpiValueDTO monthlyKpiValueDTO = new MonthlyKpiValueDTO();

        monthlyKpiValueDTO.setId( s.getId() );
        monthlyKpiValueDTO.setLabel( s.getLabel() );
        monthlyKpiValueDTO.setDate( s.getDate() );
        monthlyKpiValueDTO.setValue( s.getValue() );
        monthlyKpiValueDTO.setType( s.getType() );
        monthlyKpiValueDTO.setStatus( s.getStatus() );
        monthlyKpiValueDTO.setMethod( s.getMethod() );
        monthlyKpiValueDTO.setKpi( kpiToKpiDTO( s.getKpi() ) );

        return monthlyKpiValueDTO;
    }

    @Override
    public MonthlyKpiValueDTO toDtoId(MonthlyKpiValue monthlyKpiValue) {
        if ( monthlyKpiValue == null ) {
            return null;
        }

        MonthlyKpiValueDTO monthlyKpiValueDTO = new MonthlyKpiValueDTO();

        monthlyKpiValueDTO.setId( monthlyKpiValue.getId() );

        return monthlyKpiValueDTO;
    }

    protected KpiDTO kpiToKpiDTO(Kpi kpi) {
        if ( kpi == null ) {
            return null;
        }

        KpiDTO kpiDTO = new KpiDTO();

        kpiDTO.setId( kpi.getId() );
        kpiDTO.setName( kpi.getName() );
        kpiDTO.setReference( kpi.getReference() );
        if ( kpi.getCreationDate() != null ) {
            kpiDTO.setCreationDate( DateTimeFormatter.ISO_LOCAL_DATE.format( kpi.getCreationDate() ) );
        }
        kpiDTO.setLastModifiedBy( kpi.getLastModifiedBy() );
        if ( kpi.getLastModifiedDate() != null ) {
            kpiDTO.setLastModifiedDate( DateTimeFormatter.ISO_LOCAL_DATE.format( kpi.getLastModifiedDate() ) );
        }

        return kpiDTO;
    }
}
