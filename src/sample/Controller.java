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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Controller {

    @FXML
    private TextField nameTextField;

    @FXML
    private Button connectButton;

    @FXML
    private Label connectionStatusLabel;
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;

    @FXML
    private VBox BoardBox;
    private Board Board = new Board();

    @FXML
    private Tab gameTab;

    private Circle Clicked = null;


    @FXML
    private void handleCircleClicked(MouseEvent event)
    {
        if(((Circle)event.getSource()).getFill() == Color.BLACK)
        {
            ((Circle)event.getSource()).setFill(Color.WHITE);
            Clicked = (Circle) event.getSource();
            String id = Clicked.getId();
            String old_xy[] = id.split("x|y");
            out.println("checkMoves:" + old_xy[1] + ":" + old_xy[2]);
            String serverResponse = null;
            try {
                serverResponse = in.readLine();
                System.out.println(serverResponse);
            }

            catch (IOException e) {
                System.out.println("Server doesn't respond!"); System.exit(1);
            }

            System.out.println(serverResponse);

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

                // łączenie się z serwerem
                listenSocket();
                out.println("startGame:" + name);

                connectionStatusLabel.setText("CONNECTED");
                connectionStatusLabel.setTextFill(Color.GREEN);
                gameTab.setDisable(false);

            }
        });

        System.out.println(connectionStatusLabel.getText());
    }

    public void listenSocket(){
        try {
            socket = new Socket("localhost", 6008);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost"); System.exit(1);
        }

        catch  (IOException e) {
            System.out.println("No I/O"); //System.exit(1);
        }
    }
}
