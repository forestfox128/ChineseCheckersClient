package sample;

import javafx.scene.image.ImageView;

public class Field {
    private int x, y, player;
    private ImageView image;

    public Field(int x, int y, ImageView image)
    {
        this.x = x;
        this.y = y;
        this.image = image;
        player = 0;
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
