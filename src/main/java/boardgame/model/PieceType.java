package boardgame.model;

public enum PieceType {
    UP("red"),
    DOWN("blue");

    private final String label;

    PieceType(String s) {
        this.label = s;
    }

    public String getLabel() {
        return label;
    }
}
