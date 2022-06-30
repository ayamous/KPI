package ma.itroad.ram.kpi.service.dto;

import java.io.Serializable;
import java.util.Objects;

import ma.itroad.ram.kpi.domain.enumeration.CalculationMethod;
import ma.itroad.ram.kpi.domain.enumeration.ValRef;
import ma.itroad.ram.kpi.domain.enumeration.ValType;

/**
 * A DTO for the {@link ma.itroad.ram.kpi.domain.ControlRule} entity.
 */
public class ControlRuleDTO implements Serializable {

    private Long id;

    private ValRef referenceValue;

    private CalculationMethod calculationMethod;

    private ValType valueType;
    private Double minValue;

    private Double maxValue;

    private String errorMessage;

    private KpiDTO kpiBase;

    private KpiDTO kpiControl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ValRef getReferenceValue() {
        return referenceValue;
    }

    public void setReferenceValue(ValRef referenceValue) {
        this.referenceValue = referenceValue;
    }

    public CalculationMethod getCalculationMethod() {
        return calculationMethod;
    }

    public void setCalculationMethod(CalculationMethod calculationMethod) {
        this.calculationMethod = calculationMethod;
    }

    public ValType getValueType() {
        return valueType;
    }

    public void setValueType(ValType valueType) {
        this.valueType = valueType;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public KpiDTO getKpiBase() {
        return kpiBase;
    }

    public void setKpiBase(KpiDTO kpiBase) {
        this.kpiBase = kpiBase;
    }

    public KpiDTO getKpiControl() {
        return kpiControl;
    }

    public void setKpiControl(KpiDTO kpiControl) {
        this.kpiControl = kpiControl;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ControlRuleDTO)) {
            return false;
        }

        ControlRuleDTO controlRuleDTO = (ControlRuleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, controlRuleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ControlRuleDTO{" +
                "id=" + getId() +
                ", referenceValue='" + getReferenceValue() + "'" +
                ", calculationMethod='" + getCalculationMethod() + "'" +
                ", valueType='" + getValueType() + "'" +
                ", minValue=" + getMinValue() +
                ", maxValue=" + getMaxValue() +
                ", kpiBase=" + getKpiBase() +
                ", kpiControl=" + getKpiControl() +
                "}";
    }
}
