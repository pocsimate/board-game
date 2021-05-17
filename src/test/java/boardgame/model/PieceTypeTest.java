package boardgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTypeTest {

    PieceType pieceTypeRed = PieceType.UP;
    PieceType pieceTypeBlue = PieceType.DOWN;

    @Test
    void testGetLabel(){
        assertEquals("red", pieceTypeRed.getLabel());
        assertEquals("blue", pieceTypeBlue.getLabel());
    }
}