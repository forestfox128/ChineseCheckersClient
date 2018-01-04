package sample;

import javafx.scene.shape.Circle;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FieldTest {

    Field field;

    @Before
    public void setUp() throws Exception {
        field = new Field(5, 7, new Circle(5), 9);
    }

    @Test
    public void getX() throws Exception {
        assertEquals(field.getX(), 5);
    }

    @Test
    public void getPlayer() throws Exception {
        assertEquals(field.getPlayer(), 9);
    }

    @Test
    public void getY() throws Exception {
        assertEquals(field.getY(), 7);
    }

    @Test
    public void setPlayer() throws Exception {
        field.setPlayer(1);
        assertEquals(field.getPlayer(), 1);
    }

    @Test
    public void getCircle() throws Exception {
        assertEquals((int)field.getCircle().getRadius(), 5);
    }

}