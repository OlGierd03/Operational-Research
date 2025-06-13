package agh.or.gen;

public enum ParentSelectionType {
    ROULETTE("Ruletka"),
    TOURNAMENT("Turniej"),
    RANKING("Ranking");

    private final String label;

    ParentSelectionType(String label) {
        this.label = label;
    }

    public static ParentSelectionType fromLabel(String label) {
        for (ParentSelectionType type : values()) {
            if (type.label.equalsIgnoreCase(label)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Nieznany typ selekcji rodzica: " + label);
    }
}
