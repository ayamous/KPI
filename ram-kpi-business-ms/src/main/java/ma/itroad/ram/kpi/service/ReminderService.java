package ma.itroad.ram.kpi.service;

import java.util.List;
import java.util.Optional;

import ma.itroad.ram.kpi.service.dto.ReminderDTO;

/**
 * Service Interface for managing {@link ma.itroad.ram.kpi.domain.Reminder}.
 */
public interface ReminderService {
    /**
     * Save a reminder.
     *
     * @param reminderDTO the entity to save.
     * @return the persisted entity.
     */
    ReminderDTO save(ReminderDTO reminderDTO);

    /**
     * Partially updates a reminder.
     *
     * @param reminderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReminderDTO> partialUpdate(ReminderDTO reminderDTO);

    /**
     * Get all the reminders.
     *
     * @return the list of entities.
     */
    List<ReminderDTO> findAll();

    /**
     * Get the "id" reminder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReminderDTO> findOne(Long id);

    /**
     * Delete the "id" reminder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
