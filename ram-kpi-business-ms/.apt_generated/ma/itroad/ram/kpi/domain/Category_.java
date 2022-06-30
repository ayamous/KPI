package ma.itroad.ram.kpi.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Category.class)
public abstract class Category_ {

	public static volatile SingularAttribute<Category, String> description;
	public static volatile SingularAttribute<Category, Long> id;
	public static volatile SingularAttribute<Category, String> label;
	public static volatile SetAttribute<Category, Kpi> kpis;

	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String LABEL = "label";
	public static final String KPIS = "kpis";

}

