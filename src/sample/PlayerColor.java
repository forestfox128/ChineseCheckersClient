package sample;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class PlayerColor {
    public static Color getColor(int player)
    {
        switch(player)
        {
            case 1:
                return Color.RED;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.BLUE;
            case 4:
                return Color.SALMON;
            case 5:
                return Color.YELLOW;
            case 6:
                return Color.PURPLE;
            default:
                return Color.BLACK;
        }
    }

    public static int isPlayerColor(Paint paint)
    {
            if(paint == Color.RED)
                return 1;
            else if(paint == Color.GREEN)
                return 2;
            else if(paint == Color.BLUE)
                return 3;
            else if(paint == Color.SALMON)
                return 4;
            else if(paint == Color.YELLOW)
                return 5;
            else if(paint == Color.PURPLE)
                return 6;
            else
                return 0;
    }
}
