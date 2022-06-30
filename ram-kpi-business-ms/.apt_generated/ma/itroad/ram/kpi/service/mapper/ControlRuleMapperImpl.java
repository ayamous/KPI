package ma.itroad.ram.kpi.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import ma.itroad.ram.kpi.domain.ControlRule;
import ma.itroad.ram.kpi.service.dto.ControlRuleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-25T17:30:23+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.2 (Eclipse Adoptium)"
)
@Component
public class ControlRuleMapperImpl implements ControlRuleMapper {

    @Autowired
    private KpiMapper kpiMapper;

    @Override
    public ControlRule toEntity(ControlRuleDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ControlRule controlRule = new ControlRule();

        controlRule.setId( dto.getId() );
        controlRule.setReferenceValue( dto.getReferenceValue() );
        controlRule.setCalculationMethod( dto.getCalculationMethod() );
        controlRule.setValueType( dto.getValueType() );
        controlRule.setMinValue( dto.getMinValue() );
        controlRule.setMaxValue( dto.getMaxValue() );
        controlRule.kpiBase( kpiMapper.toEntity( dto.getKpiBase() ) );
        controlRule.kpiControl( kpiMapper.toEntity( dto.getKpiControl() ) );
        controlRule.setErrorMessage( dto.getErrorMessage() );

        return controlRule;
    }

    @Override
    public List<ControlRule> toEntity(List<ControlRuleDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ControlRule> list = new ArrayList<ControlRule>( dtoList.size() );
        for ( ControlRuleDTO controlRuleDTO : dtoList ) {
            list.add( toEntity( controlRuleDTO ) );
        }

        return list;
    }

    @Override
    public List<ControlRuleDTO> toDto(List<ControlRule> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ControlRuleDTO> list = new ArrayList<ControlRuleDTO>( entityList.size() );
        for ( ControlRule controlRule : entityList ) {
            list.add( toDto( controlRule ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(ControlRule entity, ControlRuleDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getReferenceValue() != null ) {
            entity.setReferenceValue( dto.getReferenceValue() );
        }
        if ( dto.getCalculationMethod() != null ) {
            entity.setCalculationMethod( dto.getCalculationMethod() );
        }
        if ( dto.getValueType() != null ) {
            entity.setValueType( dto.getValueType() );
        }
        if ( dto.getMinValue() != null ) {
            entity.setMinValue( dto.getMinValue() );
        }
        if ( dto.getMaxValue() != null ) {
            entity.setMaxValue( dto.getMaxValue() );
        }
        if ( dto.getKpiBase() != null ) {
            entity.kpiBase( kpiMapper.toEntity( dto.getKpiBase() ) );
        }
        if ( dto.getKpiControl() != null ) {
            entity.kpiControl( kpiMapper.toEntity( dto.getKpiControl() ) );
        }
        if ( dto.getErrorMessage() != null ) {
            entity.setErrorMessage( dto.getErrorMessage() );
        }
    }

    @Override
    public ControlRuleDTO toDto(ControlRule s) {
        if ( s == null ) {
            return null;
        }

        ControlRuleDTO controlRuleDTO = new ControlRuleDTO();

        controlRuleDTO.setId( s.getId() );
        controlRuleDTO.setReferenceValue( s.getReferenceValue() );
        controlRuleDTO.setCalculationMethod( s.getCalculationMethod() );
        controlRuleDTO.setValueType( s.getValueType() );
        controlRuleDTO.setMinValue( s.getMinValue() );
        controlRuleDTO.setMaxValue( s.getMaxValue() );
        controlRuleDTO.setKpiBase( kpiMapper.toDto( s.getKpiBase() ) );
        controlRuleDTO.setKpiControl( kpiMapper.toDto( s.getKpiControl() ) );
        controlRuleDTO.setErrorMessage( s.getErrorMessage() );

        return controlRuleDTO;
    }
}
