package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class LoginFormController {

    public TextField txtUserName;

    Socket clientSocket = null;
    ArrayList clientArrayList = new ArrayList();



    public void loginOnAction(ActionEvent actionEvent) throws IOException {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/ChattingForm.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        clientSocket = new Socket("localhost",5000);
        clientArrayList.add(txtUserName.getText());
    }
}



/*------------------------------------server Class-----------------------------*/

/*
import java.io.IOException;
        import java.net.ServerSocket;
        import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer(){
        try {

            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("A new Client has connected");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeServerSocket(){
        try{
            if (serverSocket != null){
                serverSocket.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6000);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}*/


//---------------------------------------client-------------------------------

/*
 import java.io.*;
         import java.net.Socket;
         import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username) {
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        }catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public void sendMessage(){
        try{
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()){
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username + ": "+ messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;

                while (socket.isConnected()){
                    try{
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    }catch (IOException e){
                        closeEverything(socket,bufferedReader,bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket,BufferedReader reader,BufferedWriter writer){
        try{
            if (reader != null){
                reader.close();
            }
            if (writer != null){
                writer.close();
            }
            if (socket != null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username for the group chat : ");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost", 6000);
        Client client = new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();
    }
}*/


//-------------------------------------clientHandler---------------------------------

/*public static ArrayList<ClientHandler> clientHandlers = new ArrayList();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    public ClientHandler(Socket socket) {
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER :" + clientUsername + " has entered the chat!");
        }catch (IOException e) {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()){
            try{
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            }catch (IOException e){
                closeEverything(socket,bufferedReader,bufferedWriter);
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend){
        for (ClientHandler clientHandler : clientHandlers){
            try{
                if (!clientHandler.clientUsername.equals(clientUsername)){
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }catch (IOException e){
                closeEverything(socket,bufferedReader,bufferedWriter);
            }
        }
    }

    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("SERVER :" + clientUsername + " has left the chat!");
    }

    public void closeEverything(Socket socket,BufferedReader reader,BufferedWriter writer){
        removeClientHandler();
        try{
            if (reader != null){
                reader.close();
            }
            if (writer != null){
                writer.close();
            }
            if (socket != null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}*/