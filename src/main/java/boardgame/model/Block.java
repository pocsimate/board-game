package boardgame.model;

public class Block {

    Position position;

    public Block(Position position){
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public String toString() {
        return "Block" + position.toString();
    }

}
