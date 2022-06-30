package ma.itroad.ram.kpi.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MonthlyKpiValueHistory.class)
public abstract class MonthlyKpiValueHistory_ extends ma.itroad.ram.kpi.domain.audit.Auditable_ {

	public static volatile SingularAttribute<MonthlyKpiValueHistory, LocalDate> date;
	public static volatile SingularAttribute<MonthlyKpiValueHistory, Long> id;
	public static volatile SingularAttribute<MonthlyKpiValueHistory, Double> value;
	public static volatile SingularAttribute<MonthlyKpiValueHistory, MonthlyKpiValue> monthlyKpiValue;

	public static final String DATE = "date";
	public static final String ID = "id";
	public static final String VALUE = "value";
	public static final String MONTHLY_KPI_VALUE = "monthlyKpiValue";

}

