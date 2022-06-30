package ma.itroad.ram.kpi.domain.enumeration;

public enum EstimationMethod {
    Valeur("Valeur"),
    Ponderation("Pondération"),
    PonderationCumulee("Pondération cumulée");

    private final String value;

    EstimationMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
