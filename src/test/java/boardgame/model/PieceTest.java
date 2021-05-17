package boardgame.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    Piece pieceUp = new Piece(PieceType.UP, new Position(2,2));
    List<Direction> validDirectionsPieceUp = new ArrayList<>(Arrays.asList(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_RIGHT));

    Piece pieceDown = new Piece(PieceType.DOWN, new Position(4,3));
    List<Direction> validDirectionsPieceDown = new ArrayList<>(Arrays.asList(Direction.UP, Direction.UP_LEFT, Direction.UP_RIGHT));

    @Test
    void testGetValidDirections(){
        assertEquals(validDirectionsPieceUp, pieceUp.getValidDirections());
        assertEquals(validDirectionsPieceDown, pieceDown.getValidDirections());
    }

    @Test
    void testGetPosition(){
        assertEquals(new Position(2,2), pieceUp.getPosition());
        assertEquals(new Position(4,3), pieceDown.getPosition());
    }

    @Test
    void testGetType(){
        assertSame(PieceType.UP, pieceUp.getType());
        assertSame(PieceType.DOWN, pieceDown.getType());
    }
}