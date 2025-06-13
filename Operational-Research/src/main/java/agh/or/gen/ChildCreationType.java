package agh.or.gen;

public enum ChildCreationType {
    TOP("Top"),
    MIX("Mix"),
    BOTTOM("Bottom");

    private final String label;

    ChildCreationType(String label) {
        this.label = label;
    }

    public static ChildCreationType fromLabel(String label) {
        for (ChildCreationType type : values()) {
            if (type.label.equalsIgnoreCase(label)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Nieznany typ tworzenia dziecka: " + label);
    }
}
