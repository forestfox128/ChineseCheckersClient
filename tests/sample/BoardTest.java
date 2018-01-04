package sample;

import javafx.scene.shape.Circle;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    Board board;
    @Before
    public void setUp() throws Exception {
        board = new Board();
        board.setField(new Field(5, 5, new Circle(5)));
    }

    @Test
    public void getFields() throws Exception {
        assertEquals((int)board.getFields()[5][5].getCircle().getRadius(), 5);
    }

    @Test
    public void setField() throws Exception {
        board.setField(new Field(7, 3, new Circle(3)));
        assertEquals((int)board.getField(7, 3).getCircle().getRadius(), 3);
    }

    @Test
    public void getField() throws Exception {
        assertEquals((int)board.getField(5, 5).getCircle().getRadius(), 5);
    }

}