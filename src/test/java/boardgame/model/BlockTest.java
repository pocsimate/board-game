package boardgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    Block block = new Block(new Position(2,2));

    @Test
    void testGetPosition(){
        assertEquals(new Position(2,2), block.getPosition());
    }

    @Test
    void testToString(){
        assertEquals("Block(2,2)", block.toString());
    }

}