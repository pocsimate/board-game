package boardgame.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest {

    ArrayList<ArrayList<Piece>> illegalPieces = new ArrayList<>() {{
        add(
            new ArrayList<>() {{
                add(new Piece(PieceType.UP, new Position(0, 0)));
                add(new Piece(PieceType.UP, new Position(0, 0)));
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
    }};

    Block[] illegalBlocks = new Block[] {
            new Block(new Position(3,2)),
            new Block(new Position(2,4))
    };

    BoardGame boardGame = new BoardGame();
    List<Position> positionList = new ArrayList<>(Arrays.asList(
            new Position(0, 0), new Position(0, 1),
            new Position(0, 2), new Position(0, 3),
            new Position(0, 4), new Position(0, 5),
            new Position(0, 6)));

    @Test
    void testGetPiecePositions(){
        assertEquals(positionList, boardGame.getPiecePositions());
    }

    @Test
    void testGetPiecePosition(){
        assertEquals(new Position(0,6), boardGame.getPiecePosition(0,6));
    }

    @Test
    void testCheckPositions_shouldThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> new BoardGame(illegalPieces, illegalBlocks));
    }

    @Test
    void testisValidMove_shouldThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> boardGame.isValidMove(7, Direction.DOWN));
    }

}