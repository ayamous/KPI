package ma.itroad.ram.kpi.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ma.itroad.ram.kpi.domain.enumeration.KpiValueType;
import ma.itroad.ram.kpi.domain.enumeration.Status;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Line.class)
public abstract class Line_ {

	public static volatile SingularAttribute<Line, Period> period;
	public static volatile SingularAttribute<Line, LocalDate> from;
	public static volatile SingularAttribute<Line, Long> id;
	public static volatile SingularAttribute<Line, LocalDate> to;
	public static volatile SingularAttribute<Line, KpiValueType> version;
	public static volatile SingularAttribute<Line, Status> status;

	public static final String PERIOD = "period";
	public static final String FROM = "from";
	public static final String ID = "id";
	public static final String TO = "to";
	public static final String VERSION = "version";
	public static final String STATUS = "status";

}

