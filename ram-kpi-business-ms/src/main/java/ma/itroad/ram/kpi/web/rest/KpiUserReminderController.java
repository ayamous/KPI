package ma.itroad.ram.kpi.web.rest;


import ma.itroad.ram.kpi.service.KpiUserReminderService;
import ma.itroad.ram.kpi.service.dto.KpiUserReminderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * REST controller for managing {@link ma.itroad.ram.kpi.domain.MonthlyKpiValue}.
 */
@RestController
@RequestMapping("/business/api")
public class KpiUserReminderController {

    private final Logger log = LoggerFactory.getLogger(MonthlyKpiValueResource.class);


    private final KpiUserReminderService kpiUsersReminderService;

    public KpiUserReminderController(KpiUserReminderService kpiUsersReminderService) {
        this.kpiUsersReminderService = kpiUsersReminderService;
    }


    /**
     * {@code GET  /monthly-kpi-values} : get all the monthlyKpiValues.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of monthlyKpiValues in body.
     */
    @GetMapping("/reminder")
    public ResponseEntity<List<KpiUserReminderDTO>> getAllMonthlyKpiValues(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable, HttpServletRequest request
    ) {
        log.debug("REST request to get a page of MonthlyKpiValues");
        String token = "";
        final String authorizationHeaderValue = request.getHeader("Authorization");
        if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Bearer")) {
            token = authorizationHeaderValue.substring(7, authorizationHeaderValue.length());
        }
        List<KpiUserReminderDTO> list = kpiUsersReminderService.findAll(token);
        return ResponseEntity.ok().body(list);
    }
}
