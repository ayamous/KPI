package ma.itroad.ram.kpi.service.Validator;

import lombok.Data;

@Data
public class KpiValRefs {

    private Double baseKpiValue;
    private Double baseKpiPreviousValue;
    private Double controlKpiValue;
    private Double controlKpiPreviousValue;

}
