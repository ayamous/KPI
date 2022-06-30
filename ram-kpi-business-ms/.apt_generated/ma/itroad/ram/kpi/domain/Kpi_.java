package ma.itroad.ram.kpi.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ma.itroad.ram.kpi.domain.enumeration.EstimationMethod;
import ma.itroad.ram.kpi.domain.enumeration.EstimationPeriod;
import ma.itroad.ram.kpi.domain.enumeration.Periodicity;
import ma.itroad.ram.kpi.domain.enumeration.Source;
import ma.itroad.ram.kpi.domain.enumeration.Status;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Kpi.class)
public abstract class Kpi_ extends ma.itroad.ram.kpi.domain.audit.Auditable_ {

	public static volatile SetAttribute<Kpi, SystemSource> systemSources;
	public static volatile SingularAttribute<Kpi, Status> reminder;
	public static volatile ListAttribute<Kpi, Document> documents;
	public static volatile SingularAttribute<Kpi, Kpi> inductor;
	public static volatile SingularAttribute<Kpi, Date> created;
	public static volatile SingularAttribute<Kpi, Integer> productionPeriod;
	public static volatile SingularAttribute<Kpi, String> description;
	public static volatile ListAttribute<Kpi, MonthlyKpiValue> monthlyKpiValues;
	public static volatile SingularAttribute<Kpi, Source> source;
	public static volatile SingularAttribute<Kpi, Boolean> isEstimable;
	public static volatile SingularAttribute<Kpi, String> reference;
	public static volatile SingularAttribute<Kpi, Boolean> isInductor;
	public static volatile SingularAttribute<Kpi, Periodicity> perdiodicity;
	public static volatile SingularAttribute<Kpi, Entite> entite;
	public static volatile SingularAttribute<Kpi, EstimationMethod> estimationMethod;
	public static volatile SingularAttribute<Kpi, String> name;
	public static volatile SingularAttribute<Kpi, KpiGroup> kpiGroup;
	public static volatile SingularAttribute<Kpi, String> dirId;
	public static volatile SingularAttribute<Kpi, EstimationPeriod> estimationPeriod;
	public static volatile SingularAttribute<Kpi, Integer> estimationPeriodValue;
	public static volatile SingularAttribute<Kpi, Long> id;
	public static volatile SingularAttribute<Kpi, Category> category;
	public static volatile SingularAttribute<Kpi, Status> status;

	public static final String SYSTEM_SOURCES = "systemSources";
	public static final String REMINDER = "reminder";
	public static final String DOCUMENTS = "documents";
	public static final String INDUCTOR = "inductor";
	public static final String CREATED = "created";
	public static final String PRODUCTION_PERIOD = "productionPeriod";
	public static final String DESCRIPTION = "description";
	public static final String MONTHLY_KPI_VALUES = "monthlyKpiValues";
	public static final String SOURCE = "source";
	public static final String IS_ESTIMABLE = "isEstimable";
	public static final String REFERENCE = "reference";
	public static final String IS_INDUCTOR = "isInductor";
	public static final String PERDIODICITY = "perdiodicity";
	public static final String ENTITE = "entite";
	public static final String ESTIMATION_METHOD = "estimationMethod";
	public static final String NAME = "name";
	public static final String KPI_GROUP = "kpiGroup";
	public static final String DIR_ID = "dirId";
	public static final String ESTIMATION_PERIOD = "estimationPeriod";
	public static final String ESTIMATION_PERIOD_VALUE = "estimationPeriodValue";
	public static final String ID = "id";
	public static final String CATEGORY = "category";
	public static final String STATUS = "status";

}

