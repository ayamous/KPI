package ma.itroad.ram.kpi.service;

import ma.itroad.ram.kpi.service.dto.KpiUserReminderDTO;

import java.util.List;

public interface KpiUserReminderService {

    /**
     * Get all the Kpi User need a reminder.
     *
     * @return the list of entities.
     */
    List<KpiUserReminderDTO> findAll(String token);
}
