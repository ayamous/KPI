package ma.itroad.ram.kpi.service;

import ma.itroad.ram.kpi.domain.enumeration.CalculationMethod;
import ma.itroad.ram.kpi.service.Validator.KpiValRefs;
import ma.itroad.ram.kpi.service.dto.ControlRuleDTO;
import ma.itroad.ram.kpi.service.dto.MonthlyKpiValueDTO;
import ma.itroad.ram.kpi.web.rest.errors.ResourceNotFoundException;

import java.io.IOException;


public interface KpiValRefValues {

    KpiValRefs getKpiValRefValues(MonthlyKpiValueDTO monthlyKpiValueDTO, ControlRuleDTO controlRuleDTO, CalculationMethod calculationMethod) throws ResourceNotFoundException, IOException;

}
