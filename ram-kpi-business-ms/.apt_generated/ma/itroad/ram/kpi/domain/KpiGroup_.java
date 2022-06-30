package ma.itroad.ram.kpi.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(KpiGroup.class)
public abstract class KpiGroup_ {

	public static volatile SetAttribute<KpiGroup, Assignment> assignments;
	public static volatile SingularAttribute<KpiGroup, Long> id;
	public static volatile SingularAttribute<KpiGroup, String> label;
	public static volatile SetAttribute<KpiGroup, Kpi> kpis;

	public static final String ASSIGNMENTS = "assignments";
	public static final String ID = "id";
	public static final String LABEL = "label";
	public static final String KPIS = "kpis";

}

