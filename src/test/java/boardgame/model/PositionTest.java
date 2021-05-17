package boardgame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    Position position;

    @BeforeEach
    void initPosition(){
        position = new Position(3,3);
    }

    @Test
    void testMoveTo(){
        assertEquals(new Position(2, 3), position.moveTo(Direction.UP));
        assertEquals(new Position(4, 3), position.moveTo(Direction.DOWN));
        assertEquals(new Position(2, 2), position.moveTo(Direction.UP_LEFT));
        assertEquals(new Position(2, 4), position.moveTo(Direction.UP_RIGHT));
        assertEquals(new Position(4, 2), position.moveTo(Direction.DOWN_LEFT));
        assertEquals(new Position(4, 4), position.moveTo(Direction.DOWN_RIGHT));
    }
    @Test
    void testEquals(){
        assertEquals(position, new Position(3, 3));
    }

    @Test
    void testToString(){
        assertEquals("(3,3)", position.toString());
    }


}