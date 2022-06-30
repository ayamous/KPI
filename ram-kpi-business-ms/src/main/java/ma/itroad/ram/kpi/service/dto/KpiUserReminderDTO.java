package ma.itroad.ram.kpi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KpiUserReminderDTO {
    private String userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private List<KpiReminderInfoDTO> kpis ;

}
