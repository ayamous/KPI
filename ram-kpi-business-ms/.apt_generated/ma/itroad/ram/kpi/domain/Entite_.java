package ma.itroad.ram.kpi.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Entite.class)
public abstract class Entite_ {

	public static volatile SingularAttribute<Entite, String> description;
	public static volatile SingularAttribute<Entite, Long> id;
	public static volatile SingularAttribute<Entite, String> label;
	public static volatile SetAttribute<Entite, Kpi> kpis;

	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String LABEL = "label";
	public static final String KPIS = "kpis";

}

