package ma.itroad.ram.kpi.service.impl;

import ma.itroad.ram.kpi.service.AlgebraicFormulas;
import ma.itroad.ram.kpi.service.Validator.KpiValRefs;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AlgebraicFormulasImpl implements AlgebraicFormulas {


    @Override
    public Double difference(KpiValRefs kpiValRefs) {
            Double algebraicValue = (kpiValRefs.getBaseKpiValue() - kpiValRefs.getControlKpiPreviousValue());
            System.out.println("algebraicValue : " + algebraicValue);
            return algebraicValue;
    }


    @Override
    public Double variation(KpiValRefs kpiValRefs) {
        System.out.println("kpiValRefs : " + kpiValRefs.toString());
        if (kpiValRefs.getBaseKpiValue() != null && kpiValRefs.getBaseKpiPreviousValue() != null && kpiValRefs.getControlKpiValue() != null && kpiValRefs.getControlKpiPreviousValue() != null) {
            if (kpiValRefs.getBaseKpiPreviousValue() != 0 && kpiValRefs.getControlKpiPreviousValue() != 0) {
                Double algebraicValue = (kpiValRefs.getBaseKpiValue() / kpiValRefs.getBaseKpiPreviousValue()) - (kpiValRefs.getControlKpiValue() / kpiValRefs.getControlKpiPreviousValue());
                System.out.println("algebraicValue : " + algebraicValue);
                return algebraicValue;
            }
        }
        return null;
    }

    @Override
    public Double ratio(KpiValRefs kpiValRefs) {
        if (kpiValRefs.getBaseKpiValue() != null && kpiValRefs.getBaseKpiPreviousValue() != null && kpiValRefs.getControlKpiValue() != null && kpiValRefs.getControlKpiPreviousValue() != null) {
            if (kpiValRefs.getControlKpiValue() != 0 && kpiValRefs.getControlKpiPreviousValue() != 0) {
                Double algebraicValue = (kpiValRefs.getBaseKpiValue() / kpiValRefs.getControlKpiValue()) - (kpiValRefs.getBaseKpiPreviousValue() / kpiValRefs.getControlKpiPreviousValue());
                System.out.println("algebraicValue : " + algebraicValue);
                return algebraicValue;
            }
        }
        return null;
    }

}
