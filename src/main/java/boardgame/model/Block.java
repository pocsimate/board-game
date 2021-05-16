package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Block {

    private final ObjectProperty<Position> position = new SimpleObjectProperty<>();
    private String color;

    public Block(Position position, String color){
        this.position.set(position);
        this.color = color;
    }

    public Position getPosition() {
        return position.get();
    }

    public String getColor(){
        return color;
    }

    public String toString() {
        return color + position.get().toString();
    }

}
