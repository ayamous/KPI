package ma.itroad.ram.kpi.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ma.itroad.ram.kpi.domain.enumeration.InsertionMethod;
import ma.itroad.ram.kpi.domain.enumeration.KpiValueType;
import ma.itroad.ram.kpi.domain.enumeration.Status;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MonthlyKpiValue.class)
public abstract class MonthlyKpiValue_ {

	public static volatile SingularAttribute<MonthlyKpiValue, LocalDate> date;
	public static volatile ListAttribute<MonthlyKpiValue, MonthlyKpiValueHistory> monthlyKpiValueHistories;
	public static volatile SingularAttribute<MonthlyKpiValue, InsertionMethod> method;
	public static volatile SingularAttribute<MonthlyKpiValue, Kpi> kpi;
	public static volatile SingularAttribute<MonthlyKpiValue, Long> id;
	public static volatile SingularAttribute<MonthlyKpiValue, String> label;
	public static volatile SingularAttribute<MonthlyKpiValue, KpiValueType> type;
	public static volatile SingularAttribute<MonthlyKpiValue, Double> value;
	public static volatile SingularAttribute<MonthlyKpiValue, Status> status;

	public static final String DATE = "date";
	public static final String MONTHLY_KPI_VALUE_HISTORIES = "monthlyKpiValueHistories";
	public static final String METHOD = "method";
	public static final String KPI = "kpi";
	public static final String ID = "id";
	public static final String LABEL = "label";
	public static final String TYPE = "type";
	public static final String VALUE = "value";
	public static final String STATUS = "status";

}

