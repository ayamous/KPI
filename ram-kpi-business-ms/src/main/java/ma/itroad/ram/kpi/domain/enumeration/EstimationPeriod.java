package ma.itroad.ram.kpi.domain.enumeration;

/**
 * The EstimationPeriod enumeration.
 */
public enum EstimationPeriod {
    M("Mois"),
    N("Annee");
    private final String value;

    EstimationPeriod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
