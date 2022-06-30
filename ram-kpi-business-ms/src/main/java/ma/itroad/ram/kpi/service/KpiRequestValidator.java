package ma.itroad.ram.kpi.service;

import ma.itroad.ram.kpi.service.dto.KpiRequestValidationErrorsDTO;
import ma.itroad.ram.kpi.service.dto.MonthlyKpiValueDTO;
import ma.itroad.ram.kpi.web.rest.errors.ResourceNotFoundException;

import java.io.IOException;

public interface KpiRequestValidator {
    KpiRequestValidationErrorsDTO validate(MonthlyKpiValueDTO monthlyKpiValueDto) throws ResourceNotFoundException, IOException;
}
