package ma.itroad.ram.kpi.domain.enumeration;

/**
 * The ProductionPeriod enumeration.
 */
public enum ProductionPeriod {
    MonthPlus15Days("M + 15");

    private final String value;

    ProductionPeriod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
