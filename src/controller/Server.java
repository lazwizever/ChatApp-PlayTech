package controller;


import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static Socket accept = null;


    public static void main(String[] args) throws IOException {
        FileWriter fileWriter = new FileWriter("src/db/DB.txt");
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("server created");
        accept = serverSocket.accept();
        System.out.println("client connected");


        while (true){

            InputStreamReader isr = new InputStreamReader(accept.getInputStream());
            BufferedReader bfr = new BufferedReader(isr);
            String clientRecord = bfr.readLine();

            if (!clientRecord.equals("exit")){
                fileWriter.write(clientRecord);
                fileWriter.flush();
            }

        }

    }

}
