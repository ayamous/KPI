package ma.itroad.ram.kpi.domain.audit;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Audit.class)
public abstract class Audit_ {

	public static volatile SingularAttribute<Audit, Date> date;
	public static volatile SingularAttribute<Audit, String> by;
	public static volatile SingularAttribute<Audit, Long> id;
	public static volatile SingularAttribute<Audit, AuditOp> operation;
	public static volatile SingularAttribute<Audit, Long> objectId;

	public static final String DATE = "date";
	public static final String BY = "by";
	public static final String ID = "id";
	public static final String OPERATION = "operation";
	public static final String OBJECT_ID = "objectId";

}

