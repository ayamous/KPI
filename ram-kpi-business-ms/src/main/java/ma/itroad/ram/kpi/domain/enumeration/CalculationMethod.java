package ma.itroad.ram.kpi.domain.enumeration;


/**
 * The CalculationMethod enumeration.
 */
public enum CalculationMethod {
    Variation("Variation"),
    Difference("Difference"),
    Ratio("Ratio");

    private final String value;

    CalculationMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

