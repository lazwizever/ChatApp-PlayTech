package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChattingFormController {
    public JFXTextField txtMessage;
    public JFXTextArea messageDescription;

    Socket accept= null;
    Socket clientSocket = null;

    public void initialize(){

        new Thread(()->{

            try {
                ServerSocket serverSocket = new ServerSocket(5000);
                clientSocket = new Socket("localhost",5000);
                accept = serverSocket.accept();


                //-----------------------------------Print client side message-----------------

                InputStreamReader isrServer = new InputStreamReader(accept.getInputStream());
                BufferedReader brServer = new BufferedReader(isrServer);
                String clientRecord = brServer.readLine();
                System.out.println("clientRecord "+clientRecord);
                String userName = new LoginFormController().userName;

                while (!isrServer.equals(null)){
                    if (!clientRecord.equals("exit")){
                        messageDescription.setText(userName+" : "+clientRecord);

                    }else {
                        isrServer.close();
                    }

                }



                //-----------------------------------Print server side message-----------------

                /*InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String record = bufferedReader.readLine();*/

            }catch (Exception e){
                e.printStackTrace();
            }

        }).start();
    }


    public void sendMessageOnAction(ActionEvent actionEvent) throws IOException {
        PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
        printWriter.println(txtMessage.getText());
        System.out.println("text message "+txtMessage.getText());
        printWriter.flush();
        txtMessage.clear();


    }
}
