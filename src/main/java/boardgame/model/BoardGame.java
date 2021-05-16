package boardgame.model;

import java.util.*;

public class BoardGame {

    private static final int BOARD_SIZE_ROWNUM = 6;
    private static final int BOARD_SIZE_COLNUM = 7;

    private ArrayList<ArrayList<Piece>> pieces = new ArrayList<ArrayList<Piece>>();
    int activePlayer = 0;

    public BoardGame() {
        Piece[] top = new Piece[BOARD_SIZE_COLNUM];
        Piece[] bottom = new Piece[BOARD_SIZE_COLNUM];
        for (int i = 0; i < BOARD_SIZE_COLNUM; i++) {
            top[i] = new Piece(PieceType.UP, new Position(0, i));
            bottom[i] = new Piece(PieceType.DOWN, new Position(5, i));
        }
        ArrayList<Piece> playerOne = new ArrayList<>();
        ArrayList<Piece> playerTwo = new ArrayList<>();

        Collections.addAll(playerOne, top);
        Collections.addAll(playerTwo,bottom);

        this.pieces.add(playerOne);
        this.pieces.add(playerTwo);
        this.activePlayer = 0;
    }

    public Position getPiecePosition(int player, int index) {
        return pieces.get(player).get(index).getPosition(); // pieces[pieceNumber].getPosition();
    }

    public PieceType getPieceType(int player ,int index) {
        return pieces.get(player).get(index).getType();
    }

    public int getPieceSize(){
        return pieces.size();
    }

    public int getPieceCount(int player) {
        return pieces.get(player).size();
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (var row : pieces) {
            for (var item : row){
                joiner.add(item.toString());
            }
        }
        return joiner.toString();
    }

    public static void main(String[] args) {
        BoardGame boardGame = new BoardGame();
        System.out.println(boardGame);

        System.out.println(boardGame.getPieceCount(0));

        ArrayList<ArrayList<Piece>> pieces = new ArrayList<ArrayList<Piece>>();

        ArrayList<Piece> playerOne = new ArrayList<Piece>();
        Piece[] friend = new Piece[2];
        friend[0] = new Piece(PieceType.DOWN, new Position(0,0));
        friend[1] = new Piece(PieceType.DOWN, new Position(0,1));
        Collections.addAll(playerOne,friend);

        ArrayList<Piece> playerTwo = new ArrayList<Piece>();
        Piece[] opponent = new Piece[2];
        opponent[0] = new Piece(PieceType.UP, new Position(BOARD_SIZE_ROWNUM,0));
        opponent[1] = new Piece(PieceType.UP, new Position(BOARD_SIZE_ROWNUM,1));
        Collections.addAll(playerTwo,opponent);

        pieces.add(playerOne);
        pieces.add(playerTwo);

        System.out.println(pieces);

        int activePlayer=1;
        pieces.get(activePlayer).remove(opponent[0]);

        System.out.println(pieces);
        System.out.println(pieces.get(0).size());
    }
}
