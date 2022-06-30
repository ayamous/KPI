package ma.itroad.ram.kpi.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import ma.itroad.ram.kpi.domain.Reminder;
import ma.itroad.ram.kpi.service.dto.ReminderDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-25T17:30:23+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.2 (Eclipse Adoptium)"
)
@Component
public class ReminderMapperImpl implements ReminderMapper {

    @Override
    public Reminder toEntity(ReminderDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Reminder reminder = new Reminder();

        reminder.setId( dto.getId() );
        reminder.setReminderDay( dto.getReminderDay() );
        reminder.setReminderDuration( dto.getReminderDuration() );

        return reminder;
    }

    @Override
    public ReminderDTO toDto(Reminder entity) {
        if ( entity == null ) {
            return null;
        }

        ReminderDTO reminderDTO = new ReminderDTO();

        reminderDTO.setId( entity.getId() );
        reminderDTO.setReminderDay( entity.getReminderDay() );
        reminderDTO.setReminderDuration( entity.getReminderDuration() );

        return reminderDTO;
    }

    @Override
    public List<Reminder> toEntity(List<ReminderDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Reminder> list = new ArrayList<Reminder>( dtoList.size() );
        for ( ReminderDTO reminderDTO : dtoList ) {
            list.add( toEntity( reminderDTO ) );
        }

        return list;
    }

    @Override
    public List<ReminderDTO> toDto(List<Reminder> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ReminderDTO> list = new ArrayList<ReminderDTO>( entityList.size() );
        for ( Reminder reminder : entityList ) {
            list.add( toDto( reminder ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Reminder entity, ReminderDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getReminderDay() != null ) {
            entity.setReminderDay( dto.getReminderDay() );
        }
        if ( dto.getReminderDuration() != null ) {
            entity.setReminderDuration( dto.getReminderDuration() );
        }
    }
}
