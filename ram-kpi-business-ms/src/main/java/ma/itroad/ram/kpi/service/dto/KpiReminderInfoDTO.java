package ma.itroad.ram.kpi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.itroad.ram.kpi.domain.enumeration.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KpiReminderInfoDTO {
    private Long id;
    private String name;
    private String reference;
    private Status status;

}
