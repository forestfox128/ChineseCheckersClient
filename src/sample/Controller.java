package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
    private Circle x8y12;

    @FXML
    private Tab gameTab;

    private Circle Clicked = null;

    @FXML
    private void handleCircleEntered(MouseEvent event)
    {
        String id = ((Node)event.getSource()).getId();
        String coordinates[] = id.split("x|y");
        System.out.println("X: " + coordinates[1]);
        System.out.println("Y: " + coordinates[2]);
    }

    @FXML
    private void handleCircleClicked(MouseEvent event)
    {
        System.out.println("DZIAŁĄM");
        if(((Circle)event.getSource()).getFill() == Color.BLACK)
        {
            ((Circle)event.getSource()).setFill(Color.WHITE);
            Clicked = (Circle) event.getSource();
        }
        else if(Clicked != null)
        {
            String id = Clicked.getId();
            String old_xy[] = id.split("x|y");
            id = ((Node)event.getSource()).getId();
            String new_xy[] = id.split("x|y");
            out.println("move:" + old_xy[1] + ":" + old_xy[2] + ":"
            + new_xy[1] + ":" + new_xy[2]);
//            String serverResponse = null;
//            try {
//                serverResponse = in.readLine();
//                System.out.println(serverResponse);
//            }
//
//            catch (IOException e) {
//                System.out.println("Server doesn't respond!"); System.exit(1);
//            }
            if(true)
            {
                Clicked.setFill(Color.web("#b96609"));
                ((Circle) event.getSource()).setFill(Color.BLACK);
                Clicked = null;
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("NIE UMIESZ SIĘ RUSZAĆ");
                alert.setHeaderText("PRZECZYTAJ ZASADY");
                alert.setContentText("ZANIM ZAGRASZ IGNORANCIE");
                alert.showAndWait();
            }

        }
    }

    @FXML
    private void handleCircleExited(MouseEvent event)
    {
        //System.out.println(((Node)event.getSource()).getId());
    }


    @FXML
    public void initialize(){

        // Just for testing purposes
        x8y12.setFill(Color.BLACK);
        Clicked = x8y12;

        connectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                String name = nameTextField.getText();

                // łączenie się z serwerem
                listenSocket();
                out.println(name);

                connectionStatusLabel.setText("CONNECTED");
                connectionStatusLabel.setTextFill(Color.GREEN);
                gameTab.setDisable(false);

            }
        });

        System.out.println(connectionStatusLabel.getText());
    }

    public void listenSocket(){
        try {
            socket = new Socket("localhost", 9999);
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
