package ma.itroad.ram.kpi.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Period.class)
public abstract class Period_ {

	public static volatile SingularAttribute<Period, String> yearRef2;
	public static volatile SingularAttribute<Period, Long> id;
	public static volatile SetAttribute<Period, Line> lines;
	public static volatile SingularAttribute<Period, String> yearRef1;

	public static final String YEAR_REF2 = "yearRef2";
	public static final String ID = "id";
	public static final String LINES = "lines";
	public static final String YEAR_REF1 = "yearRef1";

}

