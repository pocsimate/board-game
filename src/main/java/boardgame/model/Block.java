package boardgame.model;

/**
 * A class that represents the <code>Block<code/>s on the board. It is not possible to move a <code>Piece<code/> if
 * there is a <code>Block<code/> on the targeted <code>Position<code/>.
 */
public class Block {

    Position position;

    public Block(Position position){
        this.position = position;
    }

    /**
     * Returns the <code>Position</code> property of the <code>Block<code/>.
     * @return the <code>Position</code> property of the <code>Block<code/>
     */
    public Position getPosition() {
        return position;
    }

    public String toString() {
        return "Block" + position.toString();
    }

}
