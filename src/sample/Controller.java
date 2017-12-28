package sample;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
    private Button connectButton;

    @FXML
    private Label connectionStatusLabel;


    @FXML
    private VBox BoardBox;
    private Board Board = new Board();

    @FXML
    private Tab gameTab;

    private Circle Clicked = null;

    public ServerConnector serverConnector = ServerConnector.getINSTANCE();
    private String message;


    @FXML
    private void handleCircleClicked(MouseEvent event)
    {
        if(((Circle)event.getSource()).getFill() == Color.BLACK)
        {
            ((Circle)event.getSource()).setFill(Color.WHITE);
            Clicked = (Circle) event.getSource();
            String id = Clicked.getId();
            String old_xy[] = id.split("x|y");

            message = serverConnector.sendInformation("checkMoves:" + old_xy[1] + ":" + old_xy[2]);

            System.out.println(message);

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


    @FXML
    public void initialize(){

        createBoard();

        connectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                String name = nameTextField.getText();

                // łączenie się z serwerem -> sendInformation

                message = serverConnector.sendInformation("startGame:" + name);

                if(message.equals("connected")) {
                    connectionStatusLabel.setText("CONNECTED");
                    connectionStatusLabel.setTextFill(Color.GREEN);
                    gameTab.setDisable(false);
                }

            }
        });

        System.out.println(connectionStatusLabel.getText());
    }

}
