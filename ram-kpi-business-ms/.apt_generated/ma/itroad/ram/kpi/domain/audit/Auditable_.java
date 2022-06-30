package ma.itroad.ram.kpi.domain.audit;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Auditable.class)
public abstract class Auditable_ {

	public static volatile SingularAttribute<Auditable, Object> createdBy;
	public static volatile SingularAttribute<Auditable, LocalDate> lastModifiedDate;
	public static volatile SingularAttribute<Auditable, Object> lastModifiedBy;
	public static volatile SingularAttribute<Auditable, LocalDate> creationDate;

	public static final String CREATED_BY = "createdBy";
	public static final String LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String LAST_MODIFIED_BY = "lastModifiedBy";
	public static final String CREATION_DATE = "creationDate";

}

