package ma.itroad.ram.kpi.domain.enumeration;

/**
 * The PeriodVersion enumeration.
 */
public enum KpiValueType {
    Budget("Budget"),
    Realised("Realised"),
    Estimated("Estimated"),
    Stopped("Stopped");

    private final String value;

    KpiValueType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static KpiValueType fromString(String text) {
        for (KpiValueType b : KpiValueType.values()) {
            System.out.println("==================================================== " + text + " " + b );
            if (b.toString().equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}