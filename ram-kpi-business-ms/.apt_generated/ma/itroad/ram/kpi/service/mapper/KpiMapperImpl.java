package ma.itroad.ram.kpi.service.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import ma.itroad.ram.kpi.domain.Document;
import ma.itroad.ram.kpi.domain.Kpi;
import ma.itroad.ram.kpi.domain.MonthlyKpiValue;
import ma.itroad.ram.kpi.domain.SystemSource;
import ma.itroad.ram.kpi.service.dto.KpiDTO;
import ma.itroad.ram.kpi.service.dto.SystemSourceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-25T17:30:24+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.2 (Eclipse Adoptium)"
)
@Component
public class KpiMapperImpl implements KpiMapper {

    @Autowired
    private EntiteMapper entiteMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SystemSourceMapper systemSourceMapper;
    @Autowired
    private KpiGroupMapper kpiGroupMapper;
    @Autowired
    private DocumentMapper documentMapper;
    @Autowired
    private MonthlyKpiValueMapper monthlyKpiValueMapper;

    @Override
    public List<Kpi> toEntity(List<KpiDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Kpi> list = new ArrayList<Kpi>( dtoList.size() );
        for ( KpiDTO kpiDTO : dtoList ) {
            list.add( toEntity( kpiDTO ) );
        }

        return list;
    }

    @Override
    public List<KpiDTO> toDto(List<Kpi> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<KpiDTO> list = new ArrayList<KpiDTO>( entityList.size() );
        for ( Kpi kpi : entityList ) {
            list.add( toDto( kpi ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Kpi entity, KpiDTO dto) {
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
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( dto.getReminder() != null ) {
            entity.setReminder( dto.getReminder() );
        }
        if ( dto.getReference() != null ) {
            entity.setReference( dto.getReference() );
        }
        if ( dto.getDirId() != null ) {
            entity.setDirId( dto.getDirId() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getPerdiodicity() != null ) {
            entity.setPerdiodicity( dto.getPerdiodicity() );
        }
        if ( dto.getEstimationMethod() != null ) {
            entity.setEstimationMethod( dto.getEstimationMethod() );
        }
        if ( dto.getIsEstimable() != null ) {
            entity.isEstimable( dto.getIsEstimable() );
        }
        if ( dto.getIsInductor() != null ) {
            entity.isInductor( dto.getIsInductor() );
        }
        if ( dto.getProductionPeriod() != null ) {
            entity.setProductionPeriod( dto.getProductionPeriod() );
        }
        if ( dto.getInductor() != null ) {
            entity.inductor( toEntity( dto.getInductor() ) );
        }
        if ( dto.getEntite() != null ) {
            entity.entite( entiteMapper.toEntity( dto.getEntite() ) );
        }
        if ( dto.getCategory() != null ) {
            entity.category( categoryMapper.toEntity( dto.getCategory() ) );
        }
        if ( entity.getSystemSources() != null ) {
            Set<SystemSource> set = systemSourceDTOSetToSystemSourceSet( dto.getSystemSources() );
            if ( set != null ) {
                entity.getSystemSources().clear();
                entity.getSystemSources().addAll( set );
            }
        }
        else {
            Set<SystemSource> set = systemSourceDTOSetToSystemSourceSet( dto.getSystemSources() );
            if ( set != null ) {
                entity.systemSources( set );
            }
        }
        if ( dto.getKpiGroup() != null ) {
            entity.kpiGroup( kpiGroupMapper.toEntity( dto.getKpiGroup() ) );
        }
        if ( entity.getDocuments() != null ) {
            List<Document> list = documentMapper.toEntity( dto.getDocuments() );
            if ( list != null ) {
                entity.getDocuments().clear();
                entity.getDocuments().addAll( list );
            }
        }
        else {
            List<Document> list = documentMapper.toEntity( dto.getDocuments() );
            if ( list != null ) {
                entity.documents( list );
            }
        }
        if ( entity.getMonthlyKpiValues() != null ) {
            List<MonthlyKpiValue> list1 = monthlyKpiValueMapper.toEntity( dto.getMonthlyKpiValues() );
            if ( list1 != null ) {
                entity.getMonthlyKpiValues().clear();
                entity.getMonthlyKpiValues().addAll( list1 );
            }
        }
        else {
            List<MonthlyKpiValue> list1 = monthlyKpiValueMapper.toEntity( dto.getMonthlyKpiValues() );
            if ( list1 != null ) {
                entity.monthlyKpiValues( list1 );
            }
        }
        if ( dto.getSource() != null ) {
            entity.setSource( dto.getSource() );
        }
        if ( dto.getEstimationPeriod() != null ) {
            entity.setEstimationPeriod( dto.getEstimationPeriod() );
        }
        if ( dto.getEstimationPeriodValue() != null ) {
            entity.setEstimationPeriodValue( dto.getEstimationPeriodValue() );
        }
    }

    @Override
    public KpiDTO toDto(Kpi s) {
        if ( s == null ) {
            return null;
        }

        KpiDTO kpiDTO = new KpiDTO();

        kpiDTO.setInductor( toDtoId( s.getInductor() ) );
        kpiDTO.setKpiGroup( kpiGroupMapper.toDtoId( s.getKpiGroup() ) );
        kpiDTO.setDocuments( documentMapper.toDto( s.getDocuments() ) );
        kpiDTO.setMonthlyKpiValues( monthlyKpiValueMapper.toDto( s.getMonthlyKpiValues() ) );
        kpiDTO.setId( s.getId() );
        kpiDTO.setName( s.getName() );
        kpiDTO.setReference( s.getReference() );
        kpiDTO.setDescription( s.getDescription() );
        kpiDTO.setPerdiodicity( s.getPerdiodicity() );
        kpiDTO.setEstimationMethod( s.getEstimationMethod() );
        kpiDTO.setProductionPeriod( s.getProductionPeriod() );
        kpiDTO.setEntite( entiteMapper.toDto( s.getEntite() ) );
        kpiDTO.setCategory( categoryMapper.toDto( s.getCategory() ) );
        kpiDTO.setSystemSources( systemSourceSetToSystemSourceDTOSet( s.getSystemSources() ) );
        kpiDTO.setDirId( s.getDirId() );
        kpiDTO.setStatus( s.getStatus() );
        kpiDTO.setReminder( s.getReminder() );
        kpiDTO.setSource( s.getSource() );
        kpiDTO.setEstimationPeriod( s.getEstimationPeriod() );
        kpiDTO.isEstimable( s.getIsEstimable() );
        kpiDTO.isInductor( s.getIsInductor() );
        kpiDTO.setEstimationPeriodValue( s.getEstimationPeriodValue() );
        if ( s.getCreationDate() != null ) {
            kpiDTO.setCreationDate( DateTimeFormatter.ISO_LOCAL_DATE.format( s.getCreationDate() ) );
        }
        kpiDTO.setLastModifiedBy( s.getLastModifiedBy() );
        if ( s.getLastModifiedDate() != null ) {
            kpiDTO.setLastModifiedDate( DateTimeFormatter.ISO_LOCAL_DATE.format( s.getLastModifiedDate() ) );
        }

        return kpiDTO;
    }

    @Override
    public Kpi toEntity(KpiDTO kpiDTO) {
        if ( kpiDTO == null ) {
            return null;
        }

        Kpi kpi = new Kpi();

        if ( kpiDTO.getCreationDate() != null ) {
            kpi.setCreationDate( LocalDate.parse( kpiDTO.getCreationDate() ) );
        }
        kpi.setLastModifiedBy( kpiDTO.getLastModifiedBy() );
        if ( kpiDTO.getLastModifiedDate() != null ) {
            kpi.setLastModifiedDate( LocalDate.parse( kpiDTO.getLastModifiedDate() ) );
        }
        kpi.setId( kpiDTO.getId() );
        kpi.setName( kpiDTO.getName() );
        kpi.setStatus( kpiDTO.getStatus() );
        kpi.setReminder( kpiDTO.getReminder() );
        kpi.setReference( kpiDTO.getReference() );
        kpi.setDirId( kpiDTO.getDirId() );
        kpi.setDescription( kpiDTO.getDescription() );
        kpi.setPerdiodicity( kpiDTO.getPerdiodicity() );
        kpi.setEstimationMethod( kpiDTO.getEstimationMethod() );
        kpi.isEstimable( kpiDTO.getIsEstimable() );
        kpi.isInductor( kpiDTO.getIsInductor() );
        kpi.setProductionPeriod( kpiDTO.getProductionPeriod() );
        kpi.inductor( toEntity( kpiDTO.getInductor() ) );
        kpi.entite( entiteMapper.toEntity( kpiDTO.getEntite() ) );
        kpi.category( categoryMapper.toEntity( kpiDTO.getCategory() ) );
        kpi.systemSources( systemSourceDTOSetToSystemSourceSet( kpiDTO.getSystemSources() ) );
        kpi.kpiGroup( kpiGroupMapper.toEntity( kpiDTO.getKpiGroup() ) );
        kpi.documents( documentMapper.toEntity( kpiDTO.getDocuments() ) );
        kpi.monthlyKpiValues( monthlyKpiValueMapper.toEntity( kpiDTO.getMonthlyKpiValues() ) );
        kpi.setSource( kpiDTO.getSource() );
        kpi.setEstimationPeriod( kpiDTO.getEstimationPeriod() );
        kpi.setEstimationPeriodValue( kpiDTO.getEstimationPeriodValue() );

        return kpi;
    }

    @Override
    public KpiDTO toDtoId(Kpi kpi) {
        if ( kpi == null ) {
            return null;
        }

        KpiDTO kpiDTO = new KpiDTO();

        kpiDTO.setId( kpi.getId() );

        return kpiDTO;
    }

    protected Set<SystemSource> systemSourceDTOSetToSystemSourceSet(Set<SystemSourceDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<SystemSource> set1 = new HashSet<SystemSource>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( SystemSourceDTO systemSourceDTO : set ) {
            set1.add( systemSourceMapper.toEntity( systemSourceDTO ) );
        }

        return set1;
    }

    protected Set<SystemSourceDTO> systemSourceSetToSystemSourceDTOSet(Set<SystemSource> set) {
        if ( set == null ) {
            return null;
        }

        Set<SystemSourceDTO> set1 = new HashSet<SystemSourceDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( SystemSource systemSource : set ) {
            set1.add( systemSourceMapper.toDto( systemSource ) );
        }

        return set1;
    }
}
