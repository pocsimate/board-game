package model;

public class Piece {

    private PieceType pieceType;
    private Position position;

    public Piece(PieceType pieceType, Position position){
        this.pieceType = pieceType;
        this.position = position;
    }

    public void move(Direction direction){
        Position position = this.position.moveTo(direction);
        this.position = position;
    }

    public static void main(String[] args) {
        Piece piece = new Piece(PieceType.BLUE, new Position(5,5));
        piece.move(Direction.of(-1,1));
        System.out.println(piece.position.toString());

        if (piece.pieceType.getPieceType().equals("blue")){
            piece.move(Direction.UP_LEFT);
            System.out.println(piece.position.toString());
        }
    }
}
