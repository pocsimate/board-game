package boardgame.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    Direction direction = Direction.of(1,1);

    @Test
    void testGetRowChange(){
        assertEquals(1, direction.getRowChange());
    }

    @Test
    void testGetColChange(){
        assertEquals(1, direction.getRowChange());
    }

    @Test
    void testOf() {
        assertSame(Direction.UP, Direction.of(-1, 0));
        assertSame(Direction.DOWN, Direction.of(1, 0));
        assertSame(Direction.UP_LEFT, Direction.of(-1, -1));
        assertSame(Direction.UP_RIGHT, Direction.of(-1, 1));
        assertSame(Direction.DOWN_LEFT, Direction.of(1, -1));
        assertSame(Direction.DOWN_RIGHT, Direction.of(1, 1));
    }

    @Test
    void testOf_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Direction.of(0, 0));
        assertThrows(IllegalArgumentException.class, () -> Direction.of(2, 2));
    }

}