package sample;

import javafx.scene.shape.Circle;

public class Field {
    private int x, y, player;
    private Circle circle;

    public Field(int x, int y, Circle circle)
    {
        this.x = x;
        this.y = y;
        this.circle = circle;
        player = 0;
    }

    public Field(int x, int y, Circle circle, int player)
    {
        this.x = x;
        this.y = y;
        this.circle = circle;
        this.player = player;
    }

    public int getX() {
        return x;
    }

    public int getPlayer() {
        return player;
    }

    public int getY() {
        return y;
    }

    public void setPlayer(int player)
    {
        this.player = player;
    }
}
