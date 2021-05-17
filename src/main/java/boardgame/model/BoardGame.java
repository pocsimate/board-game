package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.tinylog.Logger;

import java.util.*;

public class BoardGame {

    private static final int BOARD_SIZE_NUMBER_OF_ROWS = 6;
    private static final int BOARD_SIZE_NUMBER_OF_COLUMNS = 7;

    private final ObjectProperty<Boolean> isWon = new SimpleObjectProperty<>();
    private final ArrayList<ArrayList<Piece>> pieces;
    public int activePlayer;
    public Block[] blocks;

    public BoardGame() {
        this(
            new ArrayList<>() {{
                add(
                    new ArrayList<>() {{
                        add(new Piece(PieceType.UP, new Position(0, 0)));
                        add(new Piece(PieceType.UP, new Position(0, 1)));
                        add(new Piece(PieceType.UP, new Position(0, 2)));
                        add(new Piece(PieceType.UP, new Position(0, 3)));
                        add(new Piece(PieceType.UP, new Position(0, 4)));
                        add(new Piece(PieceType.UP, new Position(0, 5)));
                        add(new Piece(PieceType.UP, new Position(0, 6)));
                    }}
                );
                add(
                    new ArrayList<>() {{
                        add(new Piece(PieceType.DOWN, new Position(5, 0)));
                        add(new Piece(PieceType.DOWN, new Position(5, 1)));
                        add(new Piece(PieceType.DOWN, new Position(5, 2)));
                        add(new Piece(PieceType.DOWN, new Position(5, 3)));
                        add(new Piece(PieceType.DOWN, new Position(5, 4)));
                        add(new Piece(PieceType.DOWN, new Position(5, 5)));
                        add(new Piece(PieceType.DOWN, new Position(5, 6)));
                    }}
                );
            }},
            new Block[] {
                    new Block(new Position(3,2)),
                    new Block(new Position(2,4))
            }
        );
    }

    public BoardGame(ArrayList<ArrayList<Piece>> pieces, Block[] blocks){
        checkPositions(pieces, blocks);
        this.pieces = new ArrayList<>(pieces);
        this.blocks = blocks.clone();
        this.isWon.set(Boolean.FALSE);
        this.activePlayer = 0;
    }

    private void checkPositions(ArrayList<ArrayList<Piece>> pieces, Block[] blocks){
        Logger.debug("checkPositions running");
        var seen = new HashSet<Position>();
        for (var outer : pieces){
            for (var inner : outer){
                if(isNotOnBoard(inner.getPosition()) || seen.contains(inner.getPosition())){
                    throw new IllegalArgumentException();
                }
                seen.add(inner.getPosition());
            }
        }
        for (var block : blocks){
            if (isNotOnBoard(block.getPosition()) || seen.contains(block.getPosition())){
                throw new IllegalArgumentException();
            }
            seen.add(block.getPosition());
        }
    }

    public List<Position> getPiecePositions() {
        List<Position> positions = new ArrayList<>();
        for (var piece : pieces.get(activePlayer)) {
            positions.add(piece.getPosition());
        }
        Logger.info("POSITIONS: {}", positions);
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

        if (isNotOnBoard(newPosition)) {
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

    public static boolean isNotOnBoard(Position position) {
        return 0 > position.row() || position.row() >= BOARD_SIZE_NUMBER_OF_ROWS
                || 0 > position.col() || position.col() >= BOARD_SIZE_NUMBER_OF_COLUMNS;
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
        BoardGame boardGame = new BoardGame();
        System.out.println(boardGame);

        System.out.println(boardGame.getPieceCount(0));

    }
}
