package ma.itroad.ram.kpi.domain.enumeration;

/**
 * The Periodicity enumeration.
 */
public enum Periodicity {
    Monthly("mensuel");

    private final String value;

    Periodicity(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
