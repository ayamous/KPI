package ma.itroad.ram.kpi.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import ma.itroad.ram.kpi.domain.Document;
import ma.itroad.ram.kpi.service.dto.DocumentDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-25T17:30:23+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.2 (Eclipse Adoptium)"
)
@Component
public class DocumentMapperImpl implements DocumentMapper {

    @Override
    public Document toEntity(DocumentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Document document = new Document();

        document.setId( dto.getId() );
        document.setName( dto.getName() );
        document.setFsName( dto.getFsName() );
        document.setUrl( dto.getUrl() );
        document.setDirId( dto.getDirId() );
        document.setExtension( dto.getExtension() );
        document.setContentType( dto.getContentType() );
        document.setSize( dto.getSize() );

        return document;
    }

    @Override
    public List<Document> toEntity(List<DocumentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Document> list = new ArrayList<Document>( dtoList.size() );
        for ( DocumentDTO documentDTO : dtoList ) {
            list.add( toEntity( documentDTO ) );
        }

        return list;
    }

    @Override
    public List<DocumentDTO> toDto(List<Document> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DocumentDTO> list = new ArrayList<DocumentDTO>( entityList.size() );
        for ( Document document : entityList ) {
            list.add( toDto( document ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Document entity, DocumentDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getFsName() != null ) {
            entity.setFsName( dto.getFsName() );
        }
        if ( dto.getUrl() != null ) {
            entity.setUrl( dto.getUrl() );
        }
        if ( dto.getDirId() != null ) {
            entity.setDirId( dto.getDirId() );
        }
        if ( dto.getExtension() != null ) {
            entity.setExtension( dto.getExtension() );
        }
        if ( dto.getContentType() != null ) {
            entity.setContentType( dto.getContentType() );
        }
        entity.setSize( dto.getSize() );
    }

    @Override
    public DocumentDTO toDto(Document s) {
        if ( s == null ) {
            return null;
        }

        DocumentDTO documentDTO = new DocumentDTO();

        documentDTO.setId( s.getId() );
        documentDTO.setName( s.getName() );
        documentDTO.setFsName( s.getFsName() );
        documentDTO.setUrl( s.getUrl() );
        documentDTO.setDirId( s.getDirId() );
        documentDTO.setExtension( s.getExtension() );
        documentDTO.setContentType( s.getContentType() );
        documentDTO.setSize( s.getSize() );

        return documentDTO;
    }
}
