package ma.itroad.ram.kpi.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ma.itroad.ram.kpi.domain.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link Document} entity.
 */

public class DocumentDTO implements Serializable {

    private Long id;
    /**
     * The firstname attribute.
     */
    @Schema(description = "The firstname attribute.")
    private String name;

    private String fsName;

    private String url;

    private String dirId;

    private String extension;

    private String contentType;

    private long size;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFsName() {
        return fsName;
    }

    public void setFsName(String fsName) {
        this.fsName = fsName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDirId() {
        return dirId;
    }

    public void setDirId(String dirId) {
        this.dirId = dirId;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentDTO)) {
            return false;
        }

        DocumentDTO documentDTO = (DocumentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, documentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "DocumentDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fsName='" + fsName + '\'' +
                ", url='" + url + '\'' +
                ", dirId='" + dirId + '\'' +
                ", extension='" + extension + '\'' +
                ", contentType='" + contentType + '\'' +
                ", size=" + size +
                '}';
    }
}
