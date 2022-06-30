package ma.itroad.ram.kpi.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SystemSource.class)
public abstract class SystemSource_ {

	public static volatile SingularAttribute<SystemSource, String> description;
	public static volatile SingularAttribute<SystemSource, Long> id;
	public static volatile SingularAttribute<SystemSource, String> label;
	public static volatile SetAttribute<SystemSource, Kpi> kpis;

	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String LABEL = "label";
	public static final String KPIS = "kpis";

}

