package ma.itroad.ram.kpi.domain.audit;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;

import ma.itroad.ram.kpi.domain.date.config.AppConstants;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
//import static javax.persistence.TemporalType.TIMESTAMP;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @CreatedBy
    protected U createdBy;

    @CreatedDate
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstants.datePatern)
    //@Temporal(TIMESTAMP)
    protected LocalDate creationDate;

    @LastModifiedBy
    protected U lastModifiedBy;

    @LastModifiedDate
   // @Temporal(TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstants.datePatern)
    protected LocalDate lastModifiedDate;

    public U getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public U getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Auditable(U createdBy, LocalDate creationDate, U lastModifiedBy, LocalDate lastModifiedDate) {
        super();
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }

    public Auditable() {
        super();
/*        this.createdBy = (U) "Platform";
        this.creationDate = new Date();
        this.lastModifiedBy = (U) "Platform";
        this.lastModifiedDate = new Date();*/
    }

    @PrePersist
    public void prePersist() {
        this.creationDate = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastModifiedDate = LocalDate.now();
    }


}