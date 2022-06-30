package ma.itroad.ram.kpi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KpiUserReminder {

    private String userId;
    private Long id;
    private String name;
    private String reference;

}