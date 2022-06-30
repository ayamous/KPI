package ma.itroad.ram.kpi.service.mapper;

import ma.itroad.ram.kpi.domain.Reminder;
import ma.itroad.ram.kpi.service.dto.ReminderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reminder} and its DTO {@link ReminderDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReminderMapper extends EntityMapper<ReminderDTO, Reminder> {
}
