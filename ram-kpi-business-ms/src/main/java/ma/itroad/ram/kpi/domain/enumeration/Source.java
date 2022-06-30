package ma.itroad.ram.kpi.domain.enumeration;

/**
 * The Source enumeration.
 */
public enum Source {
    Budget("Budget"),
    Realised("Arreté sinon réalisé sinon estimé");

    private final String value;

    Source(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
