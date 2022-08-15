package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;

import java.io.*;
import java.net.Socket;

public class ChattingFormController {
    public JFXTextField txtMessage;
    public JFXTextArea messageDescription;

    Socket accept= null;
    LoginFormController loginFormController;

    static String userName;

    public void initialize(){

        new Thread(()->{

            try {
                accept = new Socket("localhost",5000);

                //-----------------------------------Print clients' message-----------------

                InputStreamReader isrServer = new InputStreamReader(accept.getInputStream());
                BufferedReader brServer = new BufferedReader(isrServer);
                String clientRecord = brServer.readLine();
                System.out.println("clientRecord "+clientRecord);
                //String userName = new LoginFormController().userName;

                while (accept.isConnected()){
                    messageDescription.setText(loginFormController.txtUserName.getText()+" : "+clientRecord);

                }


            }catch (Exception e){
                e.printStackTrace();
            }

        }).start();
    }


    public void sendMessageOnAction(ActionEvent actionEvent) throws IOException {
        PrintWriter printWriter = new PrintWriter(accept.getOutputStream());
        printWriter.println(userName+" : "+txtMessage.getText()+"/");
        printWriter.flush();
        txtMessage.clear();

        FileReader fileReader = new FileReader("src/db/DB.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String record = bufferedReader.readLine();

        String message = null;

        while (record != null ){
            String[] split = record.split("/");
            for (String temp : split) {
                message = (message == null)? temp+"\n" : message+temp+"\n";
            }

            record = bufferedReader.readLine();
            System.out.println(message);

        }
        messageDescription.setText(message);
    }
}
