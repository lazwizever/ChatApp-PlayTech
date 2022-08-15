package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChattingFormController {
    public JFXTextField txtMessage;
    public JFXTextArea messageDescription;

    Socket accept= null;
    LoginFormController loginFormController;

    public void initialize(){

        new Thread(()->{

            try {

                //-----------------------------------Print clients' message-----------------

                InputStreamReader isrServer = new InputStreamReader(accept.getInputStream());
                BufferedReader brServer = new BufferedReader(isrServer);
                String clientRecord = brServer.readLine();
                System.out.println("clientRecord "+clientRecord);
                //String userName = new LoginFormController().userName;

                while (loginFormController.clientSocket.isConnected()){
                    messageDescription.setText(loginFormController.txtUserName.getText()+" : "+clientRecord);

                }


            }catch (Exception e){
                e.printStackTrace();
            }

        }).start();
    }


    public void sendMessageOnAction(ActionEvent actionEvent) throws IOException {
        PrintWriter printWriter = new PrintWriter(loginFormController.clientSocket.getOutputStream());
        printWriter.println(txtMessage.getText());
        System.out.println("text message "+txtMessage.getText());
        printWriter.flush();
        txtMessage.clear();


    }
}
