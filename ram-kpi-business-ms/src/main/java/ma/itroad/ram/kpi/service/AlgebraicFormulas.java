package ma.itroad.ram.kpi.service;

import ma.itroad.ram.kpi.domain.Kpi;
import ma.itroad.ram.kpi.service.Validator.KpiValRefs;
import ma.itroad.ram.kpi.service.dto.ControlRuleDTO;
import ma.itroad.ram.kpi.service.dto.MonthlyKpiValueDTO;

public interface AlgebraicFormulas {

    Double variation(KpiValRefs kpiValRefs);

    Double difference(KpiValRefs kpiValRefs);

    Double ratio(KpiValRefs kpiValRefs);
}
