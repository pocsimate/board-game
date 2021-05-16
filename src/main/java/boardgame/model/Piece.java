package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Piece {

    private PieceType type;
    private final ObjectProperty<Position> position = new SimpleObjectProperty<>();

    public Piece(PieceType type, Position position) {
        this.type = type;
        this.position.set(position);
    }

    public void moveTo(Direction direction) {
        Position newPosition = position.get().moveTo(direction);
        position.set(newPosition);
    }
    public List<Direction> getValidDirections(){
        return switch (this.type){
            case UP -> new ArrayList<Direction>(Arrays.asList(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_RIGHT));
            case DOWN -> new ArrayList<Direction>(Arrays.asList(Direction.UP, Direction.UP_LEFT, Direction.UP_RIGHT));
        };
    }

    public PieceType getType() {
        return type;
    }

    public Position getPosition() {
        return position.get();
    }

    public ObjectProperty<Position> positionProperty() {
        return position;
    }

    public String toString() {
        return type.toString() + position.get().toString();
    }

}
