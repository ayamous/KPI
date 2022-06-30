package ma.itroad.ram.kpi.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ma.itroad.ram.kpi.domain.Reminder} entity.
 */
public class ReminderDTO implements Serializable {

    private Long id;

    private Integer reminderDay;

    private Integer reminderDuration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReminderDay() {
        return reminderDay;
    }

    public void setReminderDay(Integer reminderDay) {
        this.reminderDay = reminderDay;
    }

    public Integer getReminderDuration() {
        return reminderDuration;
    }

    public void setReminderDuration(Integer reminderDuration) {
        this.reminderDuration = reminderDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReminderDTO)) {
            return false;
        }

        ReminderDTO reminderDTO = (ReminderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reminderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReminderDTO{" +
                "id=" + getId() +
                ", reminderDay=" + getReminderDay() +
                ", reminderDuration=" + getReminderDuration() +
                "}";
    }
}
