package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class that represents an actual piece on the board.
 */
public class Piece {

    private final PieceType type;
    private final ObjectProperty<Position> position = new SimpleObjectProperty<>();

    public Piece(PieceType type, Position position) {
        this.type = type;
        this.position.set(position);
    }

    /**
     * Sets the position based on the moving direction.
     * @param direction the direction where the piece is moved
     */
    public void moveTo(Direction direction) {
        Position newPosition = position.get().moveTo(direction);
        position.set(newPosition);
    }

    /**
     * Returns the possible movement directions of the given piece depending on the type of it.
     * @return the possible movement directions of the given piece depending on the type of it
     */
    public List<Direction> getValidDirections(){
        return switch (this.type){
            case UP -> new ArrayList<>(Arrays.asList(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_RIGHT));
            case DOWN -> new ArrayList<>(Arrays.asList(Direction.UP, Direction.UP_LEFT, Direction.UP_RIGHT));
        };
    }

    /**
     * Returns the type of the piece.
     * @return the type of the piece
     */
    public PieceType getType() {
        return type;
    }

    /**
     * Returns the position of the piece.
     * @return the position of the piece
     */
    public Position getPosition() {
        return position.get();
    }

    /**
     * Returns the <code>ObjectProperty<code/>.
     * @return the <code>ObjectProperty<code/>
     */
    public ObjectProperty<Position> positionProperty() {
        return position;
    }

    public String toString() {
        return type.toString() + position.get().toString();
    }

}
