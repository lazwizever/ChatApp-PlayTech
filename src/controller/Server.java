package controller;


import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        final int PORT = 5000;
        ServerSocket serverSocket = new ServerSocket(PORT);

        new Thread(() -> {
            try {

                while (!serverSocket.isClosed()) {
                    Socket acceptedSocket = serverSocket.accept();
                    System.out.println("A New Client Connected");

                    ClientHandler clientsHandler = new ClientHandler(acceptedSocket);

                    new Thread(clientsHandler).start();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


    }

}
