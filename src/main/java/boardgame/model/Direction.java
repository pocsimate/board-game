package boardgame.model;

/**
 * An <code>enum</code> class which represents the possible movements on the board.
 */
public enum Direction {

    UP_LEFT(-1, -1),
    UP(-1, 0),
    UP_RIGHT(-1, 1),
    DOWN_RIGHT(1, 1),
    DOWN(1, 0),
    DOWN_LEFT(1, -1);

    private final int rowChange;
    private final int colChange;

    Direction(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    /**
     * Returns the change in the x-coordinate when moving a step in this
     * direction.
     *
     * @return the change in the x-coordinate when moving a step in this
     * direction
     */
    public int getRowChange() {
        return rowChange;
    }

    /**
     * Returns the change in the y-coordinate when moving a step in this
     * direction.
     *
     * @return the change in the y-coordinate when moving a step in this
     * direction
     */
    public int getColChange() {
        return colChange;
    }

    /**
     * Returns the direction that corresponds to the changes in the x-coordinate and the y-coordinate specified.
     * @param rowChange the change on the x-coordinate
     * @param colChange the change on the y-coordinate
     * @return the direction that corresponds to the changes in the x-coordinate and the y-coordinate specified
     */
    public static Direction of(int rowChange, int colChange) {
        for (var direction : values()) {
            if (direction.rowChange == rowChange && direction.colChange == colChange) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }
}