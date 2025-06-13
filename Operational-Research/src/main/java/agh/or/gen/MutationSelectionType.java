package agh.or.gen;

public enum MutationSelectionType {
    DELETE("Removal"),
    RANDOM("Random");

    private final String label;

    MutationSelectionType(String label) {
        this.label = label;
    }

    public static MutationSelectionType fromLabel(String label) {
        for (MutationSelectionType type : values()) {
            if (type.label.equalsIgnoreCase(label)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Nieznany typ mutacji dziecka: " + label);
    }
}
