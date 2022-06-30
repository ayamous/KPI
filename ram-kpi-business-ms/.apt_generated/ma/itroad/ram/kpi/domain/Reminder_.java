package ma.itroad.ram.kpi.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Reminder.class)
public abstract class Reminder_ {

	public static volatile SingularAttribute<Reminder, Integer> reminderDuration;
	public static volatile SingularAttribute<Reminder, Long> id;
	public static volatile SingularAttribute<Reminder, Integer> reminderDay;

	public static final String REMINDER_DURATION = "reminderDuration";
	public static final String ID = "id";
	public static final String REMINDER_DAY = "reminderDay";

}

