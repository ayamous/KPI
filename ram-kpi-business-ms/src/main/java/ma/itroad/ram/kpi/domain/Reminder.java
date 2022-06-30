package ma.itroad.ram.kpi.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Reminder.
 */
@Entity
@Table(name = "reminder")
public class Reminder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "reminder_day")
    private Integer reminderDay;

    @Column(name = "reminder_duration")
    private Integer reminderDuration;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Reminder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReminderDay() {
        return this.reminderDay;
    }

    public Reminder reminderDay(Integer reminderDay) {
        this.setReminderDay(reminderDay);
        return this;
    }

    public void setReminderDay(Integer reminderDay) {
        this.reminderDay = reminderDay;
    }

    public Integer getReminderDuration() {
        return this.reminderDuration;
    }

    public Reminder reminderDuration(Integer reminderDuration) {
        this.setReminderDuration(reminderDuration);
        return this;
    }

    public void setReminderDuration(Integer reminderDuration) {
        this.reminderDuration = reminderDuration;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reminder)) {
            return false;
        }
        return id != null && id.equals(((Reminder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reminder{" +
                "id=" + getId() +
                ", reminderDay=" + getReminderDay() +
                ", reminderDuration=" + getReminderDuration() +
                "}";
    }
}
