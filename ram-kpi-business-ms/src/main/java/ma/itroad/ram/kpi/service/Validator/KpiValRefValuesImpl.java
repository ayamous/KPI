package ma.itroad.ram.kpi.service.Validator;


import ma.itroad.ram.kpi.domain.enumeration.CalculationMethod;
import ma.itroad.ram.kpi.domain.enumeration.ValRef;
import ma.itroad.ram.kpi.service.KpiValRefValues;
import ma.itroad.ram.kpi.service.MonthlyKpiValueService;
import ma.itroad.ram.kpi.service.dto.ControlRuleDTO;
import ma.itroad.ram.kpi.service.dto.MonthlyKpiValueDTO;
import ma.itroad.ram.kpi.service.mapper.KpiMapper;
import ma.itroad.ram.kpi.web.rest.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

//import static sun.security.tools.keytool.Main.getStartDate;

@Service
public class KpiValRefValuesImpl implements KpiValRefValues {

    private final MonthlyKpiValueService monthlyKpiValueService;
    private final KpiMapper kpiMapper;


    public KpiValRefValuesImpl(MonthlyKpiValueService monthlyKpiValueService, KpiMapper kpiMapper) {
        this.monthlyKpiValueService = monthlyKpiValueService;
        this.kpiMapper = kpiMapper;
    }


    @Override
    public KpiValRefs getKpiValRefValues(MonthlyKpiValueDTO monthlyKpiValueDTO, ControlRuleDTO controlRuleDTO, CalculationMethod calculationMethod) throws ResourceNotFoundException, IOException {

        KpiValRefs kpiValRefs = new KpiValRefs();

        Double baseKpiValue = monthlyKpiValueDTO.getValue();
        LocalDate currentDate = monthlyKpiValueDTO.getDate();
        LocalDate previousDate = null;
        Double baseKpiPreviousValue = 0.0d, controlKpiValue = 0.0d, controlKpiPreviousValue = 0.0d;

        ValRef valRef = controlRuleDTO.getReferenceValue();

        if (valRef.equals(ValRef.M) || valRef.equals(ValRef.M_1) || valRef.equals(ValRef.N_1)) {

            previousDate = getPreviousDate(valRef, currentDate);

            switch (calculationMethod) {

                case Variation:
                case Ratio:
                    System.out.println("----------------------------------------------------" + valRef);
                    baseKpiPreviousValue = monthlyKpiValueService.getValueByKpiAndDate(kpiMapper.toEntity(controlRuleDTO.getKpiControl()), previousDate);
                    controlKpiValue = monthlyKpiValueService.getValueByKpiAndDate(kpiMapper.toEntity(controlRuleDTO.getKpiControl()), previousDate);
                    controlKpiPreviousValue = monthlyKpiValueService.getValueByKpiAndDate(kpiMapper.toEntity(controlRuleDTO.getKpiControl()), previousDate);
                    break;

                case Difference:
                    controlKpiPreviousValue = monthlyKpiValueService.getValueByKpiAndDate(kpiMapper.toEntity(controlRuleDTO.getKpiControl()), previousDate);
                    if (controlKpiPreviousValue == null) controlKpiPreviousValue = 0.0d;
                    baseKpiPreviousValue = 0.0d;
                    controlKpiValue = 0.0d;
                    break;
            }

        } else if (valRef.equals(ValRef.Fin_M)) {

            switch (calculationMethod) {

                case Variation:
                case Ratio:

                    LocalDate from = getStartDate(currentDate);
                    baseKpiPreviousValue = monthlyKpiValueService.sumValuesByKpiAndDatesBetweenStartAndEnd(kpiMapper.toEntity(controlRuleDTO.getKpiBase()), from, currentDate);
                    controlKpiValue = monthlyKpiValueService.getValueByKpiAndDate(kpiMapper.toEntity(controlRuleDTO.getKpiControl()), currentDate);
                    controlKpiPreviousValue = monthlyKpiValueService.sumValuesByKpiAndDatesBetweenStartAndEnd(kpiMapper.toEntity(controlRuleDTO.getKpiControl()), from, currentDate);

                    break;

                case Difference:
                    // how to calculate
                    LocalDate start = getStartDate(currentDate);
                    baseKpiPreviousValue = 0.0d;
                    controlKpiValue = 0.0d;
                    controlKpiPreviousValue = monthlyKpiValueService.sumValuesByKpiAndDatesBetweenStartAndEnd(kpiMapper.toEntity(controlRuleDTO.getKpiControl()), start, previousDate);
                    break;
            }


        } else if (valRef.equals(ValRef.Fin_M_1)) {

            switch (calculationMethod) {
                case Variation:
                case Ratio:
                    LocalDate currentDateMinus1Month = currentDate.minusMonths(1);
                    LocalDate from = getStartDate(currentDateMinus1Month);
                    baseKpiPreviousValue = monthlyKpiValueService.sumValuesByKpiAndDatesBetweenStartAndEnd(kpiMapper.toEntity(controlRuleDTO.getKpiBase()), from, currentDateMinus1Month);
                    controlKpiValue = monthlyKpiValueService.getValueByKpiAndDate(kpiMapper.toEntity(controlRuleDTO.getKpiControl()), currentDate);
                    controlKpiPreviousValue = monthlyKpiValueService.sumValuesByKpiAndDatesBetweenStartAndEnd(kpiMapper.toEntity(controlRuleDTO.getKpiControl()), from, currentDateMinus1Month);

                    break;

                case Difference:
                    // how to calculate
                    LocalDate start = getStartDate(currentDate);
                    baseKpiPreviousValue = 0.0d;
                    controlKpiValue = 0.0d;
                    controlKpiPreviousValue = monthlyKpiValueService.sumValuesByKpiAndDatesBetweenStartAndEnd(kpiMapper.toEntity(controlRuleDTO.getKpiControl()), start, previousDate);
                    break;
            }
        }else if (valRef.equals(ValRef.Budget)) {
            //Mois en cours sur le Budget
            // Must be fixed

            previousDate = getPreviousDate(valRef, currentDate);

            switch (calculationMethod) {
                case Variation:
                case Ratio:
                    System.out.println("----------------------------------------------------" + valRef);
                    baseKpiPreviousValue = monthlyKpiValueService.getValueByKpiAndDateAndBudgetVersionType(kpiMapper.toEntity(controlRuleDTO.getKpiControl()), previousDate);
                    controlKpiValue = monthlyKpiValueService.getValueByKpiAndDate(kpiMapper.toEntity(controlRuleDTO.getKpiControl()), previousDate);
                    controlKpiPreviousValue = monthlyKpiValueService.getValueByKpiAndDateAndBudgetVersionType(kpiMapper.toEntity(controlRuleDTO.getKpiControl()), previousDate);
                    break;

                case Difference:
                    controlKpiPreviousValue = monthlyKpiValueService.getValueByKpiAndDate(kpiMapper.toEntity(controlRuleDTO.getKpiControl()), previousDate);
                    if (controlKpiPreviousValue == null) controlKpiPreviousValue = 0.0d;
                    baseKpiPreviousValue = 0.0d;
                    controlKpiValue = 0.0d;
                    break;
            }

        }

        kpiValRefs.setBaseKpiValue(baseKpiValue);
        kpiValRefs.setBaseKpiPreviousValue(baseKpiPreviousValue);
        kpiValRefs.setControlKpiValue(controlKpiValue);
        kpiValRefs.setControlKpiPreviousValue(controlKpiPreviousValue);


        return kpiValRefs;
    }


    private LocalDate getStartDate(LocalDate ld) throws IOException {

        List<String> chunk1 = Arrays.asList("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER");
        List<String> chunk2 = Arrays.asList("NOVEMBER", "DECEMBER");
        LocalDate d = null;
        // if Months is in chunk1 then we must refer to the previous year, then start date from november
        if (ld != null) {
            System.out.println("The LocalDate is: " + ld);
            System.out.println("The month is: " + ld.getMonth());
            String month = ld.getMonth().toString();
            if (chunk1.contains(month)) {
                d = ld.minusYears(1);
                System.out.println("Month found in chunk1");
            } else if (chunk2.contains(month)) {
                System.out.println("Month found in chunk2");
            }
            // setup to November because it is the effective start of year exercise, every year start in November
            d = d.withMonth(12);

            System.out.println("d : " + d.toString());

        }
        return d;
    }

    ;

    public LocalDate getPreviousDate(ValRef valRef, LocalDate currentDate) {
        LocalDate d = null;
        switch (valRef) {
            case M:
            case Budget:
                d = currentDate;
                break;
            case M_1:
                d = currentDate.minusMonths(1);
                break;
            case N_1:
                d = currentDate.minusYears(1);
                break;
            default:
                System.out.println("Choix incorrect");
                break;
        }
        return d;
    }

}