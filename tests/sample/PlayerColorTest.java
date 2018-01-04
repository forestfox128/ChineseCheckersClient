package sample;

import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerColorTest {
    @Before

    @Test
    public void getColor() throws Exception {
        assertEquals(PlayerColor.getColor(1), Color.RED);
        assertEquals(PlayerColor.getColor(2), Color.GREEN);
        assertEquals(PlayerColor.getColor(3), Color.BLUE);
        assertEquals(PlayerColor.getColor(4), Color.SALMON);
        assertEquals(PlayerColor.getColor(5), Color.YELLOW);
        assertEquals(PlayerColor.getColor(6), Color.PURPLE);
        assertEquals(PlayerColor.getColor(6 + (int)(Math.random()*50)), Color.BLACK);
    }

    @Test
    public void isPlayerColor() throws Exception {
        assertEquals(PlayerColor.isPlayerColor(Color.RED), 1);
        assertEquals(PlayerColor.isPlayerColor(Color.GREEN), 2);
        assertEquals(PlayerColor.isPlayerColor(Color.BLUE), 3);
        assertEquals(PlayerColor.isPlayerColor(Color.SALMON), 4);
        assertEquals(PlayerColor.isPlayerColor(Color.YELLOW), 5);
        assertEquals(PlayerColor.isPlayerColor(Color.PURPLE), 6);
    }

}