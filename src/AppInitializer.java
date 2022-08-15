import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("views/LoginForm.fxml"))));
        primaryStage.show();
    }


    /*public void createServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        try {

            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("A new Client has connected");
                //ClientHandler clientHandler = new ClientHandler(socket);

                //Thread thread = new Thread(clientHandler);
                //thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/



}
