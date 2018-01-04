package sample;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Controller {

    @FXML
    private TextField nameTextField;

    @FXML
    public Label infoLabel;

    @FXML
    private Button connectButton;

    @FXML
    private Label connectionStatusLabel;

    private int myID;

    @FXML
    private VBox BoardBox;
    private Board Board = new Board();

    @FXML
    private Tab gameTab;

    private Circle Clicked = null;

    public ServerConnector serverConnector = ServerConnector.getINSTANCE();
    private String message;
    private Updater boardUpdater;

    @FXML
    private void handleCircleClicked(MouseEvent event)
    {
        if(boardUpdater.checkTurn() == myID)
        {
            if(PlayerColor.isPlayerColor(((Circle)event.getSource()).getFill()) == myID)
            {
                boardUpdater.updateBoard();
                if(Clicked != null)
                {
                    Clicked.setStrokeWidth(1.0);
                }
                Clicked = (Circle) event.getSource();
                Clicked.setStrokeWidth(2.0);
                String id = Clicked.getId();
                String old_xy[] = id.split("x|y");

                message = serverConnector.sendInformation("checkMoves:" + old_xy[1] + ":" + old_xy[2]);

                String[] fields = message.split(",");
                for (String field : fields) {
                    try {
                        String[] info = field.split(":");
                        int x = Integer.parseInt(info[0]);
                        int y = Integer.parseInt(info[1]);
                        if (Board.getField(x, y) != null)
                            Board.getField(x, y).getCircle().setFill(Color.WHITE);
                    }
                    catch (NumberFormatException ex)
                    {
                        System.out.println("num ex handleClick");
                    }
                }
                System.out.println(message);
            }
            else if(((Circle)(event.getSource())).getFill() == Color.WHITE)
            {
                Circle target = ((Circle)(event.getSource()));
                String coordinates[] = getCoordinates(target);
                String clickedCoordinates[] = getCoordinates(Clicked);
                movePawn(Integer.parseInt(clickedCoordinates[1]), Integer.parseInt(clickedCoordinates[2]),
                        Integer.parseInt(coordinates[1]), Integer.parseInt(coordinates[2]));
            }
        }
    }

    @FXML
    private void handleCircleEntered(){};

    @FXML
    private void handleCircleExited(){};


    void createBoard(){
        ObservableList HBoxes = BoardBox.getChildren();
        for(Object object: HBoxes)
        {
            HBox hbox = (HBox)object;
            ObservableList FieldCircles = hbox.getChildren();
            for(Object circleObject: FieldCircles)
            {
                Circle circle = (Circle)circleObject;
                String id = circle.getId();
                String xy[] = id.split("x|y");
                try {
                    Field field = new Field(Integer.parseInt(xy[1]), Integer.parseInt(xy[2]), circle);
                    Board.setField(field);
                    circle.setFill(Color.BLACK);
                }
                catch (NumberFormatException ex)
                {
                    System.out.println(id + "exception :(");
                    System.out.println(xy[1]);
                    System.out.println(xy[2]);
                }

            }
        }

    }

    private void movePawn (int oldX, int oldY, int newX, int newY)
    {
        String message = serverConnector.sendInformation("move:" + oldX + ":" + oldY
        + ":" + newX + ":" + newY + ":" + myID);
        boardUpdater.resume();
    }

    private String[] getCoordinates (Circle circle)
    {
        String id = circle.getId();
        String xy[] = id.split("x|y");
        return xy;
    }


    @FXML
    public void initialize(){

        connectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                String name = nameTextField.getText();

                // łączenie się z serwerem -> sendInformation

                message = serverConnector.sendInformation("startGame:" + name);

                if(message.substring(0,9).equals("connected")) {
                    connectionStatusLabel.setText("CONNECTED");
                    connectionStatusLabel.setTextFill(Color.GREEN);
                    gameTab.setDisable(false);
                    createBoard();
                    myID = Integer.parseInt(message.substring(10));
                    System.out.println("Moje id to " + myID);
                    boardUpdater = new Updater(serverConnector, Board, myID, infoLabel);
                    boardUpdater.start();
                }

            }
        });

        System.out.println(connectionStatusLabel.getText());
    }

}
