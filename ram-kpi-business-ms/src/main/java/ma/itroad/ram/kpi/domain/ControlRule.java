package ma.itroad.ram.kpi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import javax.persistence.*;

import ma.itroad.ram.kpi.domain.enumeration.CalculationMethod;
import ma.itroad.ram.kpi.domain.enumeration.ValRef;
import ma.itroad.ram.kpi.domain.enumeration.ValType;

/**
 * A ControlRule.
 */
@Entity
@Table(name = "control_rule")
public class ControlRule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_value")
    private ValRef referenceValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "calculation_method")
    private CalculationMethod calculationMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "value_type")
    private ValType valueType;

    @Column(name = "min_value")
    private Double minValue;

    @Column(name = "max_value")
    private Double maxValue;

    @Column(name = "error_message")
    private String errorMessage;

    @JsonIgnoreProperties(
            value = {"documents", "inductor", "kpiGroup", "entite", "category", "systemSource", "kpiGroup"},
            allowSetters = true
    )
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(unique = true)
    private Kpi kpiBase;

    @JsonIgnoreProperties(
            value = {"documents", "inductor", "kpiGroup", "entite", "category", "systemSource", "kpiGroup"},
            allowSetters = true
    )
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(unique = true)
    private Kpi kpiControl;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ControlRule id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ValRef getReferenceValue() {
        return this.referenceValue;
    }

    public ControlRule referenceValue(ValRef referenceValue) {
        this.setReferenceValue(referenceValue);
        return this;
    }

    public void setReferenceValue(ValRef referenceValue) {
        this.referenceValue = referenceValue;
    }

    public CalculationMethod getCalculationMethod() {
        return this.calculationMethod;
    }

    public ControlRule calculationMethod(CalculationMethod calculationMethod) {
        this.setCalculationMethod(calculationMethod);
        return this;
    }

    public void setCalculationMethod(CalculationMethod calculationMethod) {
        this.calculationMethod = calculationMethod;
    }

    public ValType getValueType() {
        return this.valueType;
    }

    public ControlRule valueType(ValType valueType) {
        this.setValueType(valueType);
        return this;
    }

    public void setValueType(ValType valueType) {
        this.valueType = valueType;
    }

    public Double getMinValue() {
        return this.minValue;
    }

    public ControlRule minValue(Double minValue) {
        this.setMinValue(minValue);
        return this;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return this.maxValue;
    }

    public ControlRule maxValue(Double maxValue) {
        this.setMaxValue(maxValue);
        return this;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Kpi getKpiBase() {
        return this.kpiBase;
    }

    public void setKpiBase(Kpi kpi) {
        this.kpiBase = kpi;
    }

    public ControlRule kpiBase(Kpi kpi) {
        this.setKpiBase(kpi);
        return this;
    }

    public Kpi getKpiControl() {
        return this.kpiControl;
    }

    public void setKpiControl(Kpi kpi) {
        this.kpiControl = kpi;
    }

    public ControlRule kpiControl(Kpi kpi) {
        this.setKpiControl(kpi);
        return this;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public ControlRule errorMessage(String errorMessage) {
        this.setErrorMessage(errorMessage);
        return this;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ControlRule)) {
            return false;
        }
        return id != null && id.equals(((ControlRule) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "ControlRule{" +
                "id=" + id +
                ", referenceValue=" + referenceValue +
                ", calculationMethod=" + calculationMethod +
                ", valueType=" + valueType +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", errorMessage='" + errorMessage + '\'' +
                ", kpiBase=" + kpiBase +
                ", kpiControl=" + kpiControl +
                '}';
    }
}
