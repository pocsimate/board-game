package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.tinylog.Logger;

import java.util.*;

/**
 * Represents the overall state of the board game.
 */
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

    /**
     * Checks if the pieces and the blocks are on the board and if nothing is on top of each other.
     *
     * @param pieces the data structure holding the pieces of each player
     * @param blocks the array containing the blocks where pieces can not be moved
     * @throws IllegalArgumentException if the given piece or block is not on board or if it overlaps with another.
     *
     */
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

    /**
     * Returns the positions of the active player's pieces.
     *
     * @return the positions of the active player's pieces
     */
    public List<Position> getPiecePositions() {
        List<Position> positions = new ArrayList<>();
        for (var piece : pieces.get(activePlayer)) {
            positions.add(piece.getPosition());
        }
        Logger.info("POSITIONS: {}", positions);
        return positions;
    }

    /**
     * Returns the position of the given player's piece at the given index.
     *
     * @param player the given player
     * @param index the index of the given player's piece
     * @return the position of the given player's piece at the given index
     */
    public Position getPiecePosition(int player, int index) {
        return pieces.get(player).get(index).getPosition();
    }

    /**
     * Returns the position property of the given player's given piece.
     *
     * @param player the given player
     * @param pieceNumber the index of the given player's piece
     * @return the position property of the given player's given piece
     */
    public ObjectProperty<Position> positionProperty(int player, int pieceNumber) {
        return pieces.get(player).get(pieceNumber).positionProperty();
    }

    /**
     * Returns the type of the given player's given piece.
     *
     * @param player the given player
     * @param index the index of the given player's piece
     * @return he type of the given player's given piece
     */
    public PieceType getPieceType(int player ,int index) {
        return pieces.get(player).get(index).getType();
    }

    /**
     * Returns the color of the given player's given piece.
     *
     * @param player the given player
     * @param index the index of the given player's piece
     * @return the color of the given player's given piece
     */
    public String getPieceColor(int player ,int index) {
        return getPieceType(player, index).getLabel();
    }

    /**
     * Returns how many player's pieces does the data structure holds.
     *
     * @return how many player's pieces does the data structure holds
     */
    public int getPieceSize(){
        return pieces.size();
    }

    /**
     * Returns the size of the data structure that holds the given player's pieces.
     *
     * @param player the given player
     * @return he size of the data structure that holds the given player's pieces
     */
    public int getPieceCount(int player) {
        return pieces.get(player).size();
    }

    public ObjectProperty<Boolean> getIsWon(){
        return isWon;
    }

    /**
     * Returns if the active player's given piece can be moved to a given direction.
     *
     * @param pieceNumber the index of the active player's piece
     * @param direction the given direction
     * @return true if the active player's given piece can be moved to the given direction
     * @throws IllegalArgumentException if the index is out of bounds
     */
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

    /**
     * Returns if a given position is not on the board.
     *
     * @param position the position that is checked if it is on the board
     * @return true if the given position is not on board
     */
    public static boolean isNotOnBoard(Position position) {
        return 0 > position.row() || position.row() >= BOARD_SIZE_NUMBER_OF_ROWS
                || 0 > position.col() || position.col() >= BOARD_SIZE_NUMBER_OF_COLUMNS;
    }

    /**
     * Returns all the directions that the given piece of the active player can be moved to.
     *
     * @param pieceNumber the given piece of the active player
     * @return the directions that the given piece of the active player can be moved to
     */
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

    /**
     * {@return if the active player has no piece or can not step anywhere}
     */
    public boolean isWonState(){
        return (pieces.get(activePlayer).isEmpty() || hasNoAvailableStep(activePlayer));
    }

    /**
     * Returns if the given player has no available step.
     *
     * @param player the given player
     * @return true if the given player can not step in any direction
     */
    public boolean hasNoAvailableStep(int player) {
        for (int i = 0; i < getPieceCount(player); i++) {
            Set<Direction> validDirections = getValidMoves(i);
            if (!validDirections.isEmpty()){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the move of the active player's piece in the given direction overlaps the other player's piece. if so, the piece
     * of the non active player gets removed.
     *
     * @param pieceNumber the given piece of the active player
     * @param direction the given direction
     */
    public void checkForOverlap(int pieceNumber, Direction direction){
        Position newPosition = pieces.get(activePlayer).get(pieceNumber).getPosition().moveTo(direction);

        for (var piece : pieces.get(toggleActivePlayer())){
            if (piece.getPosition().equals(newPosition)) {
                deletePiece(toggleActivePlayer(), piece);
                break;
            }
        }
    }

    /**
     * Deletes the given player's given piece.
     *
     * @param player the given player
     * @param piece the given piece
     */
    public void deletePiece(int player, Piece piece){
        pieces.get(player).remove(piece);
    }

    /**
     * Returns the index of the active player's piece which is located on the given position.
     *
     * @param position the given position
     * @return the index of the piece if there is a piece of the active player at the given position.
     */
    public OptionalInt getPieceNumber(Position position) {
        for (int i = 0; i < pieces.get(activePlayer).size(); i++) {
            if (pieces.get(activePlayer).get(i).getPosition().equals(position)) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }

    /**
     * Returns the toggled value of active player.
     *
     * @return the toggled value of active player
     * @throws IllegalStateException if the active player is not zero or one.
     */
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

}
