package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

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



    @FXML
    public void initialize(){


        connectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                String name = nameTextField.getText();

                // łączenie się z serwerem
                listenSocket();
                out.println(name);

                connectionStatusLabel.setText("CONNECTED");
                connectionStatusLabel.setTextFill(Color.GREEN);
            }
        });

        System.out.println(connectionStatusLabel.getText());
    }

    public void listenSocket(){
        try {
            socket = new Socket("localhost", 9999);
            out = new PrintWriter(socket.getOutputStream(), true);
            //in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost"); System.exit(1);
        }

        catch  (IOException e) {
            System.out.println("No I/O"); //System.exit(1);
        }
    }
}
