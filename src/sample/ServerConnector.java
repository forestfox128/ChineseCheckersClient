package sample;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerConnector {


    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private static ServerConnector INSTANCE;

    private ServerConnector() {
        connectToServer();
    }

    //SINGLETON
    public static ServerConnector getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new ServerConnector();

        return INSTANCE;
    }

    public void connectToServer(){
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

    /**
     *
     * @param message wiadomość od klienta którą funkcja wysyła serwerowi
     * @return serverResponse odpowiedz servera na wyslaną wiadomość
     */

    public String sendInformation(String message){
        String serverResponse = null;

        out.println(message);

        try {
            serverResponse = in.readLine();
            System.out.println(serverResponse);
        }

        catch (IOException e) {
            System.out.println("Server doesn't respond!"); System.exit(1);
        }

        //System.out.println(serverResponse);

        return serverResponse;

    }

}
