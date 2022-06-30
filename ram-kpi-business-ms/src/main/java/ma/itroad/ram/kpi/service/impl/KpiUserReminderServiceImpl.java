package ma.itroad.ram.kpi.service.impl;

import lombok.extern.slf4j.Slf4j;
import ma.itroad.core.common.api.messaging.domain.AppMessage;
import ma.itroad.core.common.api.messaging.enums.MessageStatus;
import ma.itroad.core.common.api.messaging.enums.MessageType;
import ma.itroad.ram.kpi.domain.KpiUserReminder;
import ma.itroad.ram.kpi.domain.Reminder;
import ma.itroad.ram.kpi.domain.enumeration.Status;
import ma.itroad.ram.kpi.repository.AssignmentRepository;
import ma.itroad.ram.kpi.repository.KpiRepository;
import ma.itroad.ram.kpi.repository.ReminderRepository;
import ma.itroad.ram.kpi.service.KpiUserReminderService;
import ma.itroad.ram.kpi.service.OpenFeign.MessagingService;
import ma.itroad.ram.kpi.service.OpenFeign.UserManagementService;
import ma.itroad.ram.kpi.service.dto.KpiReminderInfoDTO;
import ma.itroad.ram.kpi.service.dto.KpiUserReminderDTO;
import ma.itroad.ram.kpi.service.dto.UserDTO;
import ma.itroad.ram.kpi.service.mapper.KpiMapper;
//import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KpiUserReminderServiceImpl implements KpiUserReminderService {

    private final ReminderRepository reminderRepository;
    private final KpiRepository kpiRepository;
    private final AssignmentRepository assignmentRepository;
    private final KpiMapper kpiMapper;
    private final MessagingService messagingService;
    private final UserManagementService userManagementService;

    public KpiUserReminderServiceImpl(ReminderRepository reminderRepository, KpiRepository kpiRepository, AssignmentRepository assignmentRepository, KpiMapper kpiMapper, MessagingService messagingService, UserManagementService userManagementService) {
        this.reminderRepository = reminderRepository;
        this.kpiRepository = kpiRepository;
        this.assignmentRepository = assignmentRepository;
        this.kpiMapper = kpiMapper;
        this.messagingService = messagingService;
        this.userManagementService = userManagementService;
    }


    @Override
    @Transactional(readOnly = true)
    public List<KpiUserReminderDTO> findAll(String token) {
        log.debug("Request to get all Reminders");
        List<KpiUserReminderDTO> list = new ArrayList<>();
        Reminder reminder = null;
        List<Reminder> reminderList = reminderRepository.findAll();
        if (reminderList.size() >= 1) {
            reminder = reminderList.get(0);
        }
        if (reminder != null) {
            Integer reminderDay = reminder.getReminderDay();
            LocalDate today = LocalDate.now();
            LocalDate firstDayOfMonth = today.with(TemporalAdjusters.firstDayOfMonth()); // 2014-07-01
            log.debug("today : " + today);
            log.debug("firstDayOfMonth : " + firstDayOfMonth);
            long days = ChronoUnit.DAYS.between(firstDayOfMonth, today);
            log.debug("days :  " + days);
            int productionPeriod = ((int) days) + reminderDay;
            List<KpiUserReminder> KpiUserReminderList = kpiRepository.fetchAll(productionPeriod, Status.Active, Status.Active);

            log.debug("kpiUsersReminderDTO : " + KpiUserReminderList);
            Map<Object, List<KpiUserReminder>> KpiUserReminderListGrouped =
                    KpiUserReminderList.stream().collect(Collectors.groupingBy(w -> w.getUserId()));
            log.debug("KpiUserReminderListGrouped : " + KpiUserReminderListGrouped);

            // using for-each loop for iteration over Map.entrySet()
            for (Map.Entry<Object, List<KpiUserReminder>> entry : KpiUserReminderListGrouped.entrySet()) {
                System.out.println("Key = " + entry.getKey() +
                        ", Value = " + entry.getValue());
                String userId = entry.getKey().toString();
                UserDTO userDTO = userManagementService.getUserById(userId).getBody();
                //getUser(userId, token);
                KpiUserReminderDTO kpiUserReminderDTO = new KpiUserReminderDTO();
                kpiUserReminderDTO.setUserId(userDTO.getId());
                kpiUserReminderDTO.setUserFirstName(userDTO.getFirstName());
                kpiUserReminderDTO.setUserLastName(userDTO.getLastName());
                kpiUserReminderDTO.setUserEmail(userDTO.getEmail());
                List<KpiReminderInfoDTO> kpis = new ArrayList<>();
                List<KpiUserReminder> kpiUserReminders = entry.getValue();
                for (KpiUserReminder kpiUserReminder : kpiUserReminders) {
                    KpiReminderInfoDTO kpiReminderInfoDTO = new KpiReminderInfoDTO();
                    kpiReminderInfoDTO.setId(kpiUserReminder.getId());
                    kpiReminderInfoDTO.setName(kpiUserReminder.getName());
                    kpiReminderInfoDTO.setReference(kpiUserReminder.getReference());
                    kpis.add(kpiReminderInfoDTO);
                }

                kpiUserReminderDTO.setKpis(kpis);
                list.add(kpiUserReminderDTO);
            }
        }

        Collection<AppMessage> messages = toAppMessageConverter(list);

        messagingService.sendMail(messages);

        return list;
    }

    private Collection<AppMessage> toAppMessageConverter(List<KpiUserReminderDTO> list) {
        Collection<AppMessage> messages = new ArrayList<>();
        for (KpiUserReminderDTO kpiUserReminderDTO : list) {
            AppMessage appMessage = new AppMessage();
            appMessage.setRecipient(kpiUserReminderDTO.getUserEmail());
            appMessage.setSender(kpiUserReminderDTO.getUserEmail());
            appMessage.setType(MessageType.EMAIL_HTML);
            appMessage.setStatus(MessageStatus.PENDING);
            appMessage.setContent("");
            appMessage.setSubject("Rappel kpi test");
            appMessage.setProviderCodeStatus("200");
            Map<String, Object> additionalProperties = new HashMap<>();
            additionalProperties.put("template", "email-verification");
            Map<String, String> params = new HashMap<>();
            if (kpiUserReminderDTO.getKpis() != null) {
                for (int i = 0; i < kpiUserReminderDTO.getKpis().size(); i++) {
                    System.out.println(kpiUserReminderDTO.getKpis().get(i));
                    params.put("kpiName " + i, kpiUserReminderDTO.getKpis().get(i).getName());
                    params.put("kpiReference " + i, kpiUserReminderDTO.getKpis().get(i).getReference());
                }
            }
            additionalProperties.put("params", params);
            appMessage.setAdditionalProperties(additionalProperties);
            messages.add(appMessage);
        }
        return messages;

    }
}