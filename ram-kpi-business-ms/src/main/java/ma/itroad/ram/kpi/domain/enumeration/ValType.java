package ma.itroad.ram.kpi.domain.enumeration;

/**
 * The ValType enumeration.
 */
public enum ValType {
    Alg("M"),
    Pct("%");

    private final String value;

    ValType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

