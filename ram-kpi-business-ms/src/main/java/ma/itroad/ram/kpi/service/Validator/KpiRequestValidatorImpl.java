package ma.itroad.ram.kpi.service.Validator;

import ma.itroad.ram.kpi.domain.enumeration.CalculationMethod;
import ma.itroad.ram.kpi.domain.enumeration.ValType;
import ma.itroad.ram.kpi.service.*;
import ma.itroad.ram.kpi.service.dto.ControlRuleDTO;
import ma.itroad.ram.kpi.service.dto.KpiRequestValidationErrorsDTO;
import ma.itroad.ram.kpi.service.dto.MonthlyKpiValueDTO;
import ma.itroad.ram.kpi.service.mapper.KpiMapper;
import ma.itroad.ram.kpi.web.rest.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class KpiRequestValidatorImpl implements KpiRequestValidator {

    private final ControlRuleService controlRuleService;
    private final KpiMapper kpiMapper;
    private final AlgebraicFormulas algebraicFormulas;
    private final KpiValRefValues kpiValRefValues;


    public KpiRequestValidatorImpl(ControlRuleService controlRuleService, KpiMapper kpiMapper, AlgebraicFormulas algebraicFormulas, KpiValRefValues kpiValRefValues) {
        this.controlRuleService = controlRuleService;
        this.kpiMapper = kpiMapper;
        this.algebraicFormulas = algebraicFormulas;
        this.kpiValRefValues = kpiValRefValues;
    }

    @Override
    public KpiRequestValidationErrorsDTO validate(MonthlyKpiValueDTO monthlyKpiValueDto) throws ResourceNotFoundException, IOException {

        Optional<ControlRuleDTO> optControlRuleDTO = controlRuleService.findByKpiBase(kpiMapper.toEntity(monthlyKpiValueDto.getKpi()));

        if (optControlRuleDTO.isPresent()) {
            ControlRuleDTO controlRuleDTO = optControlRuleDTO.get();
            CalculationMethod calculationMethod = controlRuleDTO.getCalculationMethod();
            System.out.println("calculationMethod : " + calculationMethod);
            KpiValRefs kpiValRefs = kpiValRefValues.getKpiValRefValues(monthlyKpiValueDto, controlRuleDTO, calculationMethod);
            System.out.println("KpiValRefs ===========>" + kpiValRefs);
            Double value = 0.0d;
            Double min = controlRuleDTO.getMinValue();
            Double max = controlRuleDTO.getMaxValue();

            switch (controlRuleDTO.getCalculationMethod()) {

                case Variation:
                    value = algebraicFormulas.variation(kpiValRefs);
                    System.out.println("Variation formulas : " + value);
                    break;

                case Difference:
                    value = algebraicFormulas.difference(kpiValRefs);
                    System.out.println("Difference formulas : " + value);
                    break;

                case Ratio:
                    value = algebraicFormulas.ratio(kpiValRefs);
                    System.out.println("Ratio formulas : " + value);
                    break;

                default:
                    System.out.println("CalculationMethod is not supported");
                    break;
            }

            if (controlRuleDTO.getCalculationMethod() == CalculationMethod.Ratio && controlRuleDTO.getValueType() == ValType.Pct) {
                if (kpiValRefs.getBaseKpiValue() != null && kpiValRefs.getControlKpiValue() != null) {
                    if (kpiValRefs.getControlKpiValue() != 0) {
                        Double algValue = kpiValRefs.getBaseKpiValue() / kpiValRefs.getControlKpiValue();
                        System.out.println("algValue : " + algValue);
                        min = algValue * min;
                        max = algValue * max;
                    }
                }
            }


            if (value != null && min != null && max != null && value >= min && value <= max) {
                return null;
            } else {
                KpiRequestValidationErrorsDTO validationError = new KpiRequestValidationErrorsDTO();
                validationError.setControlRuleNumber(controlRuleDTO.getId());
                validationError.setKpiName(controlRuleDTO.getKpiBase().getName());
                validationError.setErrorMessage(value != null ? controlRuleDTO.getErrorMessage() : "Valeur manquante");
                validationError.setPeriod(monthlyKpiValueDto.getDate());
                return validationError;
            }
        }
        return null;
    }

}

































 /* if(controlRuleDTO.getKpiBase()==null){
          System.out.println("base kpi is null");
          throw new ResourceNotFoundException("ControlRuleDTO baseKpi not found : "+monthlyKpiValueDto.getKpi());

          }else if(controlRuleDTO.getKpiControl()==null){
          System.out.println("control kpi is null");
          throw new ResourceNotFoundException("ControlRuleDTO controlKpi not found : "+monthlyKpiValueDto.getKpi());

          }else if(controlRuleDTO.getKpiBase().getId()==null){
          System.out.println("base kpi id is null");
          throw new BadRequestAlertException("A controlRuleDTO does not have an ID",ENTITY_NAME,"idnotexists");
          }

          else if(controlRuleDTO.getKpiBase().getId()==null){
          throw new BadRequestAlertException("Invalid id",ENTITY_NAME,"idnull");
          }*/