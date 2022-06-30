package ma.itroad.ram.kpi.domain.audit;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Audit implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 7177902742599667591L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private AuditOp operation;
    private Date date;
    private String by;
    private Long objectId;

    public AuditOp getOperation() {
        return operation;
    }

    public void setOperation(AuditOp operation) {
        this.operation = operation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public Audit(AuditOp operation, String by, long objectId) {
        super();
        this.operation = operation;
        this.date = new Date();
        this.by = by;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Audit() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

}
