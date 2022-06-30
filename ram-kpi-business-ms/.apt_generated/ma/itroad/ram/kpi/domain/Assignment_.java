package ma.itroad.ram.kpi.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Assignment.class)
public abstract class Assignment_ {

	public static volatile SingularAttribute<Assignment, KpiGroup> kpiGroup;
	public static volatile SingularAttribute<Assignment, Long> id;
	public static volatile SingularAttribute<Assignment, String> userId;

	public static final String KPI_GROUP = "kpiGroup";
	public static final String ID = "id";
	public static final String USER_ID = "userId";

}

