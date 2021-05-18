package boardgame.model;

import java.util.Objects;

/**
 * A record class which represents a position on the board.
 */
public record Position(int row, int col) {

    /**
     * Returns a new position according to the given direction.
     * @param direction the direction of the move
     * @return a new position according to the given direction
     */
    public Position moveTo(Direction direction) {
        return new Position(row + direction.getRowChange(), col + direction.getColChange());
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        return (o instanceof Position p) && p.row == row && p.col == col;
    }

    public String toString() {
        return String.format("(%d,%d)", row, col);
    }
}
