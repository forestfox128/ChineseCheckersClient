package sample;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Updater extends Thread {

    private ServerConnector connector;
    private Board board;
    private int ID;
    private Label info;

    public Updater(ServerConnector Connector, Board board, int id, Label label)
    {
        this.connector = Connector;
        this.board = board;
        this.ID = id;
        this.info = label;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try {
                System.out.println("run");
                updateBoard();
                if(checkTurn() == ID)
                    this.suspend();
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateBoard(){
        System.out.println("updateBoard");
        String msg = connector.sendInformation("getBoard");
        if(msg.substring(0,7) == "endGame")
        {
            // TODO: IMPLEMENT ENDGAME
        }
        String[] fields = msg.split(",");
        for (String field : fields) {
            try {
                String[] info = field.split(":");
                int x = Integer.parseInt(info[0]);
                int y = Integer.parseInt(info[1]);
                int player = Integer.parseInt(info[2]);
                if(board.getField(x, y) != null)
                    board.getField(x, y).getCircle().setFill(PlayerColor.getColor(player));
            } catch (NumberFormatException ex)
            {
                System.out.println("num ex updateBoard");
            }
        }
    }

    public int checkTurn()
    {
        String msg = connector.sendInformation("checkIfYourTurn:" + ID);
        if(Integer.parseInt(msg) == ID)
            Platform.runLater(new Runnable() {
                public void run() {
                    info.setText("Twoja tura!");
                }

            });
        else
            Platform.runLater(new Runnable() {
                public void run() {
                    info.setText("Rusza się " + Integer.parseInt(msg) + "...");
                }

            });
        return Integer.parseInt(msg);
    }
}
