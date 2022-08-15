package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;

import java.io.*;
import java.net.Socket;

public class ChattingFormController {
    public JFXTextField txtMessage;
    public JFXTextArea messageDescription;

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public static String userName;

    public void initialize(){

            try {
                this.socket=new Socket("localhost",5000);
                this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                this.bufferedWriter.write(userName);
                this.bufferedWriter.newLine();
                this.bufferedWriter.flush();


                //-------------------Update all Messages------------------------
                new Thread(()-> {
                    String chatMessages;
                    while (socket.isConnected()){
                        try{
                            chatMessages = bufferedReader.readLine();
                            System.out.println(chatMessages);
                            messageDescription.appendText(chatMessages+"\n");
                        }catch (IOException e){
                            closeEverything(socket,bufferedReader,bufferedWriter);
                        }
                    }

                }).start();

            }catch (Exception e){
                e.printStackTrace();
            }

    }


    public void sendMessageOnAction(ActionEvent actionEvent) throws IOException {
        String message = txtMessage.getText();
        messageDescription.appendText("Me : " + message+"\n");
        bufferedWriter.write(userName + " : " + message);
        bufferedWriter.newLine();
        bufferedWriter.flush();

        txtMessage.clear();
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


}
