package ma.itroad.ram.kpi.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ma.itroad.ram.kpi.domain.Reminder;
import ma.itroad.ram.kpi.repository.ReminderRepository;
import ma.itroad.ram.kpi.service.ReminderService;
import ma.itroad.ram.kpi.service.dto.ReminderDTO;
import ma.itroad.ram.kpi.service.mapper.ReminderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Reminder}.
 */
@Service
@Transactional
public class ReminderServiceImpl implements ReminderService {

    private final Logger log = LoggerFactory.getLogger(ReminderServiceImpl.class);

    private final ReminderRepository reminderRepository;

    private final ReminderMapper reminderMapper;

    public ReminderServiceImpl(ReminderRepository reminderRepository, ReminderMapper reminderMapper) {
        this.reminderRepository = reminderRepository;
        this.reminderMapper = reminderMapper;
    }

    @Override
    public ReminderDTO save(ReminderDTO reminderDTO) {
        log.debug("Request to save Reminder : {}", reminderDTO);
        Reminder reminder = reminderMapper.toEntity(reminderDTO);
        reminder = reminderRepository.save(reminder);
        return reminderMapper.toDto(reminder);
    }

    @Override
    public Optional<ReminderDTO> partialUpdate(ReminderDTO reminderDTO) {
        log.debug("Request to partially update Reminder : {}", reminderDTO);

        return reminderRepository
                .findById(reminderDTO.getId())
                .map(existingReminder -> {
                    reminderMapper.partialUpdate(existingReminder, reminderDTO);

                    return existingReminder;
                })
                .map(reminderRepository::save)
                .map(reminderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReminderDTO> findAll() {
        log.debug("Request to get all Reminders");
        return reminderRepository.findAll().stream().map(reminderMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReminderDTO> findOne(Long id) {
        log.debug("Request to get Reminder : {}", id);
        return reminderRepository.findById(id).map(reminderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reminder : {}", id);
        reminderRepository.deleteById(id);
    }
}
