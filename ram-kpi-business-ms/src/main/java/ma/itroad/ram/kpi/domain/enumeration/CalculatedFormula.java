package ma.itroad.ram.kpi.domain.enumeration;

/**
 * The CalculatedFormula enumeration.
 */
public enum CalculatedFormula {
    VIA_AN_ENTRY("Via une saisie"),
    VIA_AN_BUDGET("Sur la base de budget"),
    VIA_N_1("Sur la base de N - 1"),
    VIA_M_1("Sur la base de M - 1"),
    VIA_SF_VALUE("(SF m) / (SCRF m) x ICRM m"),
    VIA_ID_VALUE("(ID m) / (SCRF m) x ICRM m");

    private final String value;

    CalculatedFormula(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
