package ma.itroad.ram.kpi.service.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import ma.itroad.ram.kpi.domain.MonthlyKpiValueHistory;
import ma.itroad.ram.kpi.service.dto.MonthlyKpiValueHistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-25T17:30:23+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.2 (Eclipse Adoptium)"
)
@Component
public class MonthlyKpiValueHistoryMapperImpl implements MonthlyKpiValueHistoryMapper {

    @Autowired
    private MonthlyKpiValueMapper monthlyKpiValueMapper;

    @Override
    public MonthlyKpiValueHistory toEntity(MonthlyKpiValueHistoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MonthlyKpiValueHistory monthlyKpiValueHistory = new MonthlyKpiValueHistory();

        if ( dto.getCreationDate() != null ) {
            monthlyKpiValueHistory.setCreationDate( LocalDate.parse( dto.getCreationDate() ) );
        }
        monthlyKpiValueHistory.setLastModifiedBy( dto.getLastModifiedBy() );
        if ( dto.getLastModifiedDate() != null ) {
            monthlyKpiValueHistory.setLastModifiedDate( LocalDate.parse( dto.getLastModifiedDate() ) );
        }
        monthlyKpiValueHistory.setId( dto.getId() );
        monthlyKpiValueHistory.setValue( dto.getValue() );
        monthlyKpiValueHistory.setDate( dto.getDate() );
        monthlyKpiValueHistory.monthlyKpiValue( monthlyKpiValueMapper.toEntity( dto.getMonthlyKpiValue() ) );

        return monthlyKpiValueHistory;
    }

    @Override
    public List<MonthlyKpiValueHistory> toEntity(List<MonthlyKpiValueHistoryDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<MonthlyKpiValueHistory> list = new ArrayList<MonthlyKpiValueHistory>( dtoList.size() );
        for ( MonthlyKpiValueHistoryDTO monthlyKpiValueHistoryDTO : dtoList ) {
            list.add( toEntity( monthlyKpiValueHistoryDTO ) );
        }

        return list;
    }

    @Override
    public List<MonthlyKpiValueHistoryDTO> toDto(List<MonthlyKpiValueHistory> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MonthlyKpiValueHistoryDTO> list = new ArrayList<MonthlyKpiValueHistoryDTO>( entityList.size() );
        for ( MonthlyKpiValueHistory monthlyKpiValueHistory : entityList ) {
            list.add( toDto( monthlyKpiValueHistory ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(MonthlyKpiValueHistory entity, MonthlyKpiValueHistoryDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getCreationDate() != null ) {
            entity.setCreationDate( LocalDate.parse( dto.getCreationDate() ) );
        }
        if ( dto.getLastModifiedBy() != null ) {
            entity.setLastModifiedBy( dto.getLastModifiedBy() );
        }
        if ( dto.getLastModifiedDate() != null ) {
            entity.setLastModifiedDate( LocalDate.parse( dto.getLastModifiedDate() ) );
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getValue() != null ) {
            entity.setValue( dto.getValue() );
        }
        if ( dto.getDate() != null ) {
            entity.setDate( dto.getDate() );
        }
        if ( dto.getMonthlyKpiValue() != null ) {
            entity.monthlyKpiValue( monthlyKpiValueMapper.toEntity( dto.getMonthlyKpiValue() ) );
        }
    }

    @Override
    public MonthlyKpiValueHistoryDTO toDto(MonthlyKpiValueHistory s) {
        if ( s == null ) {
            return null;
        }

        MonthlyKpiValueHistoryDTO monthlyKpiValueHistoryDTO = new MonthlyKpiValueHistoryDTO();

        monthlyKpiValueHistoryDTO.setMonthlyKpiValue( monthlyKpiValueMapper.toDtoId( s.getMonthlyKpiValue() ) );
        monthlyKpiValueHistoryDTO.setId( s.getId() );
        monthlyKpiValueHistoryDTO.setValue( s.getValue() );
        monthlyKpiValueHistoryDTO.setDate( s.getDate() );
        if ( s.getCreationDate() != null ) {
            monthlyKpiValueHistoryDTO.setCreationDate( DateTimeFormatter.ISO_LOCAL_DATE.format( s.getCreationDate() ) );
        }
        monthlyKpiValueHistoryDTO.setLastModifiedBy( s.getLastModifiedBy() );
        if ( s.getLastModifiedDate() != null ) {
            monthlyKpiValueHistoryDTO.setLastModifiedDate( DateTimeFormatter.ISO_LOCAL_DATE.format( s.getLastModifiedDate() ) );
        }

        return monthlyKpiValueHistoryDTO;
    }
}
