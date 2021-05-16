package model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Piece {

    private PieceType type;
    private Position position;

    public void move(Direction direction){
        this.position = this.position.moveTo(direction);
    }

    public PieceType getType() {
        return type;
    }

    public Position getPosition() {
        return position;
    }

    public String toString() {
        return type.toString() + position.toString();
    }

//    public static void main(String[] args) {
//        Piece piece = new Piece(PieceType.BLUE, new Position(5,5));
//        piece.move(Direction.of(-1,1));
//        System.out.println(piece);
//
//        if (piece.pieceType.getPieceType().equals("blue")){
//            piece.move(Direction.UP_LEFT);
//            System.out.println(piece);
//        }
//    }
}
