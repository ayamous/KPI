package ma.itroad.ram.kpi.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ma.itroad.ram.kpi.domain.enumeration.CalculationMethod;
import ma.itroad.ram.kpi.domain.enumeration.ValRef;
import ma.itroad.ram.kpi.domain.enumeration.ValType;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ControlRule.class)
public abstract class ControlRule_ {

	public static volatile SingularAttribute<ControlRule, CalculationMethod> calculationMethod;
	public static volatile SingularAttribute<ControlRule, Double> minValue;
	public static volatile SingularAttribute<ControlRule, Kpi> kpiBase;
	public static volatile SingularAttribute<ControlRule, Double> maxValue;
	public static volatile SingularAttribute<ControlRule, ValType> valueType;
	public static volatile SingularAttribute<ControlRule, Kpi> kpiControl;
	public static volatile SingularAttribute<ControlRule, String> errorMessage;
	public static volatile SingularAttribute<ControlRule, Long> id;
	public static volatile SingularAttribute<ControlRule, ValRef> referenceValue;

	public static final String CALCULATION_METHOD = "calculationMethod";
	public static final String MIN_VALUE = "minValue";
	public static final String KPI_BASE = "kpiBase";
	public static final String MAX_VALUE = "maxValue";
	public static final String VALUE_TYPE = "valueType";
	public static final String KPI_CONTROL = "kpiControl";
	public static final String ERROR_MESSAGE = "errorMessage";
	public static final String ID = "id";
	public static final String REFERENCE_VALUE = "referenceValue";

}

