package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.tinylog.Logger;

import java.util.*;

public class BoardGame {

    private static final int BOARD_SIZE_ROWNUM = 6;
    private static final int BOARD_SIZE_COLNUM = 7;

    private ArrayList<ArrayList<Piece>> pieces = new ArrayList<ArrayList<Piece>>();
    public Block[] blocks = new Block[2];
    public int activePlayer;
    private final ObjectProperty<Boolean> isWon = new SimpleObjectProperty<>();

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

        this.blocks[0] = new Block(new Position(2,4));
        this.blocks[1] = new Block(new Position(3,2));

        this.pieces.add(playerOne);
        this.pieces.add(playerTwo);
        this.isWon.set(Boolean.FALSE);
        this.activePlayer = 0;
    }
    public List<Position> getPiecePositions() {
        List<Position> positions = new ArrayList<>();
        for (var piece : pieces.get(activePlayer)) {
            positions.add(piece.getPosition());
        }
        Logger.warn("POSITIONS: {}", positions);
        return positions;
    }

    public Position getPiecePosition(int player, int index) {
        return pieces.get(player).get(index).getPosition();
    }

    public ObjectProperty<Position> positionProperty(int player, int pieceNumber) {
        return pieces.get(player).get(pieceNumber).positionProperty();
    }

    public PieceType getPieceType(int player ,int index) {
        return pieces.get(player).get(index).getType();
    }

    public String getPieceColor(int player ,int index) {
        return getPieceType(player, index).getLabel();
    }

    public int getPieceSize(){
        return pieces.size();
    }

    public int getPieceCount(int player) {
        return pieces.get(player).size();
    }

    public ObjectProperty<Boolean> getIsWon(){
        return isWon;
    }

    public boolean isValidMove(int pieceNumber, Direction direction) {
        if (pieceNumber < 0 || pieceNumber >= pieces.get(activePlayer).size()) {
            throw new IllegalArgumentException();
        }

        Position newPosition = pieces.get(activePlayer).get(pieceNumber).getPosition().moveTo(direction);

        if (! isOnBoard(newPosition)) {
            return false;
        }

        for (var block : blocks){
            if (block.getPosition().equals(newPosition)){
                return false;
            }
        }

        if (direction.equals(Direction.DOWN) || direction.equals(Direction.UP)){
            for (var piece : pieces.get(toggleActivePlayer())){
                if (piece.getPosition().equals(newPosition)) {
                    return false;
                }
            }
        }

        for (var piece : pieces.get(activePlayer)) {
            if (piece.getPosition().equals(newPosition)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isOnBoard(Position position) {
        return 0 <= position.row() && position.row() < BOARD_SIZE_ROWNUM
                && 0 <= position.col() && position.col() < BOARD_SIZE_COLNUM;
    }

    public Set<Direction> getValidMoves(int pieceNumber) {
        EnumSet<Direction> validMoves = EnumSet.noneOf(Direction.class);
        for (var direction : pieces.get(activePlayer).get(pieceNumber).getValidDirections()) {
            if (isValidMove(pieceNumber, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    public void move(int pieceNumber, Direction direction) {
        checkForOverlap(pieceNumber, direction);
        pieces.get(activePlayer).get(pieceNumber).moveTo(direction);
        activePlayer = toggleActivePlayer();
        if(isWonState()){
            getIsWon().set(Boolean.TRUE);
        }
    }

    public boolean isWonState(){
        return (pieces.get(activePlayer).isEmpty() || hasNoAvailableStep(activePlayer));
    }

    public boolean hasNoAvailableStep(int player) {
        for (int i = 0; i < getPieceCount(player); i++) {
            Set<Direction> validDirections = getValidMoves(i);
            if (!validDirections.isEmpty()){
                return false;
            }
        }
        return true;
    }

    public void checkForOverlap(int pieceNumber, Direction direction){
        Position newPosition = pieces.get(activePlayer).get(pieceNumber).getPosition().moveTo(direction);

        for (var piece : pieces.get(toggleActivePlayer())){
            if (piece.getPosition().equals(newPosition)) {
                deletePiece(toggleActivePlayer(), piece);
                break;
            }
        }
    }

    public void deletePiece(int player, Piece piece){
        pieces.get(player).remove(piece);
    }

    public OptionalInt getPieceNumber(Position position) {
        for (int i = 0; i < pieces.get(activePlayer).size(); i++) {
            if (pieces.get(activePlayer).get(i).getPosition().equals(position)) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }

    public int toggleActivePlayer(){
        return switch (activePlayer){
            case 0 -> 1;
            case 1 -> 0;
            default -> throw new IllegalStateException("Unexpected value: " + activePlayer);
        };
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (var row : pieces) {
            for (var item : row){
                joiner.add(item.toString());
            }
        }
        for (var block : blocks){
            joiner.add(block.toString());
        }
        return joiner.toString();
    }

    public static void main(String[] args) {
//        BoardGame boardGame = new BoardGame();
//        System.out.println(boardGame);
//
//        System.out.println(boardGame.getPieceCount(0));

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

        pieces.get(0).remove(friend[0]);
        System.out.println(pieces);

//        int activePlayer=0;
//        System.out.println(pieces.get(activePlayer).get(1).getValidDirections().contains(Direction.UP_LEFT));
//
//        System.out.println(pieces);
//        System.out.println(pieces.get(0).size());
    }
}
