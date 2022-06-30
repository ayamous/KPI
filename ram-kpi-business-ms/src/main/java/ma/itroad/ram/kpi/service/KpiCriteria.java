package ma.itroad.ram.kpi.service;

/*import ma.itroad.ram.kpi.service.filter.LongFilter;
import ma.itroad.ram.kpi.service.filter.StringFilter;*/

import ma.itroad.ram.kpi.domain.enumeration.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

public class KpiCriteria implements Serializable, Criteria {

    private final Logger log = LoggerFactory.getLogger(KpiCriteria.class);

    /**
     * Class for filtering Status
     */
    public static class StatusFilter extends Filter<Status> {

        public StatusFilter() {
        }

        public StatusFilter(StatusFilter filter) {
            super(filter);
        }

        @Override
        public StatusFilter copy() {
            return new StatusFilter(this);
        }
    }

    private LongFilter id;

    private StringFilter name;

    private StringFilter reference;

    private LongFilter entiteId;

    private LongFilter categoryId;

    private LongFilter systemSourceId;

    private StatusFilter status;

    private StatusFilter reminder;

    private BooleanFilter isInductor;


    public KpiCriteria() {
    }

    public KpiCriteria(KpiCriteria other) {
        log.debug("KpiCriteria : {}", other);
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.entiteId = other.entiteId == null ? null : other.entiteId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.systemSourceId = other.systemSourceId == null ? null : other.systemSourceId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.reminder = other.reminder == null ? null : other.reminder.copy();
        this.isInductor = other.isInductor == null ? null : other.isInductor.copy();
    }

    @Override
    public KpiCriteria copy() {
        return new KpiCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getReference() {
        return reference;
    }

    public StringFilter reference() {
        if (reference == null) {
            reference = new StringFilter();
        }
        return reference;
    }

    public void setReference(StringFilter reference) {
        this.reference = reference;
    }

    public LongFilter getEntiteId() {
        return entiteId;
    }

    public LongFilter entiteId() {
        if (entiteId == null) {
            entiteId = new LongFilter();
        }
        return entiteId;
    }

    public void setEntiteId(LongFilter entiteId) {
        this.entiteId = entiteId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public LongFilter categoryId() {
        if (categoryId == null) {
            categoryId = new LongFilter();
        }
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getSystemSourceId() {
        return systemSourceId;
    }

    public LongFilter systemSourceId() {
        if (systemSourceId == null) {
            systemSourceId = new LongFilter();
        }
        return systemSourceId;
    }

    public void setSystemSourceId(LongFilter systemSourceId) {
        this.systemSourceId = systemSourceId;
    }


    public StatusFilter getStatus() {
        return status;
    }

    public StatusFilter status() {
        if (status == null) {
            status = new StatusFilter();
        }
        return status;
    }

    public void setStatus(StatusFilter status) {
        this.status = status;
    }

    public StatusFilter getReminder() {
        return reminder;
    }

    public StatusFilter reminder() {
        if (reminder == null) {
            reminder = new StatusFilter();
        }
        return reminder;
    }

    public void setReminder(StatusFilter reminder) {
        this.reminder = reminder;
    }


    public BooleanFilter getIsInductor() {
        return isInductor;
    }

    public BooleanFilter isInductor() {
        if (isInductor == null) {
            isInductor = new BooleanFilter();
        }
        return isInductor;
    }

    public void setIsInductor(BooleanFilter isInductor) {
        this.isInductor = isInductor;
    }

    @Override
    public String toString() {
        return "KpiCriteria{" +
                "id=" + id +
                ", name=" + name +
                ", reference=" + reference +
                ", entiteId=" + entiteId +
                ", categoryId=" + categoryId +
                ", systemSourceId=" + systemSourceId +
                ", status=" + status +
                ", reminder=" + reminder +
                ", isInductor=" + isInductor +
                '}';
    }
}
