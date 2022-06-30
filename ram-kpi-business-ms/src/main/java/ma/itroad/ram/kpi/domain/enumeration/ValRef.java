package ma.itroad.ram.kpi.domain.enumeration;

/**
 * The ValRef enumeration.
 */

public enum ValRef {
    M("Mois en cours"),
    M_1("Mois précédent"),
    N_1("le même mois M de N-1"),
    Fin_M("Cumul de l'année jusqu'au mois en cours"),
    Fin_M_1("Cumul de l'année jusqu'au mois M-1"),
    Budget("Mois en cours sur le Budget");

    private final String value;

    ValRef(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

