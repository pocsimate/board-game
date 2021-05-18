package boardgame.model;

/**
 * An <code>enum</code> class which represents the type of a <code>Piece</code>.
 */
public enum PieceType {
    UP("red"),
    DOWN("blue");

    private final String label;

    PieceType(String s) {
        this.label = s;
    }

    /**
     * Returns the color which is assigned to the <code>PieceType</code>.
     * @return the color which is assigned to the <code>PieceType</code>
     */
    public String getLabel() {
        return label;
    }
}
