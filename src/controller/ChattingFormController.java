package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;


import java.io.*;
import java.net.Socket;

public class ChattingFormController {
    public JFXTextField txtMessage;
    public JFXTextArea messageDescription;
    public ScrollPane scrollPane;
    public VBox vBOxMessage;

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

                vBOxMessage.heightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        scrollPane.setVvalue((Double) newValue);
                    }
                });


                //-------------------Update all Messages------------------------
                new Thread(()-> {
                    String chatMessages;
                    while (socket.isConnected()){
                        try{
                            chatMessages = bufferedReader.readLine();
                            if (chatMessages.startsWith("img")){

                                String[] split = chatMessages.replace("img", " ").split("-");


                                HBox hBox = new HBox();
                                hBox.setAlignment(Pos.CENTER_LEFT);
                                hBox.setPadding(new Insets(5, 5, 5, 10));

                                Text text = new Text(split[0] +" : ");
                                TextFlow textFlow = new TextFlow(text);
                                textFlow.setStyle("-fx-font-weight: bold;"+"-fx-background-color:#cf8bf6;");
                                textFlow.setPadding(new Insets(5, 10, 5, 10));
                                text.setFill(Color.color(1, 1, 1, 1));


                                ImageView imageView = new ImageView();
                                //Setting image to the image view
                                imageView.setImage(new Image(new File(split[1]).toURI().toString()));
                                //Setting the image view parameters
                                imageView.setFitWidth(300);
                                imageView.setPreserveRatio(true);

                                hBox.getChildren().add(textFlow);
                                hBox.getChildren().add(imageView);

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        vBOxMessage.getChildren().add(hBox);
                                    }
                                });

                            }else if (chatMessages.startsWith("SERVER")){

                                HBox hBox = new HBox();
                                hBox.setAlignment(Pos.CENTER);
                                hBox.setPadding(new Insets(5, 5, 5, 10));

                                Text text = new Text(chatMessages);
                                TextFlow textFlow = new TextFlow(text);

                                textFlow.setStyle("-fx-font-weight: bold;"+"-fx-background-color:#cf8bf6;" + "-fx-background-radius:10px");
                                textFlow.setPadding(new Insets(5, 10, 5, 10));
                                text.setFill(Color.color(1, 1, 1, 1));

                                hBox.getChildren().add(textFlow);

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        vBOxMessage.getChildren().add(hBox);
                                    }
                                });

                            }else {
                                HBox hBox = new HBox();
                                hBox.setAlignment(Pos.CENTER_LEFT);
                                hBox.setPadding(new Insets(5, 5, 5, 10));

                                Text text = new Text(chatMessages);
                                TextFlow textFlow = new TextFlow(text);

                                textFlow.setStyle("-fx-font-weight: bold;"+"-fx-background-color:#cf8bf6;" + "-fx-background-radius:10px");
                                textFlow.setPadding(new Insets(5, 10, 5, 10));
                                text.setFill(Color.color(1, 1, 1, 1));

                                hBox.getChildren().add(textFlow);

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        vBOxMessage.getChildren().add(hBox);
                                    }
                                });
                            }

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

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text("Me : " + message);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle("-fx-font-weight: bold;"+"-fx-background-color:#cf8bf6;" + "-fx-background-radius:10px");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.color(1, 1, 1, 1));

        hBox.getChildren().add(textFlow);
        vBOxMessage.getChildren().add(hBox);

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


    public void sendEmojiOnAction(ActionEvent actionEvent) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a Image");
        File file = fileChooser.showOpenDialog(null);

        bufferedWriter.write("img" + userName + " -" + file.getPath());
        bufferedWriter.newLine();
        bufferedWriter.flush();

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text("Me : ");
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-font-weight: bold;"+"-fx-background-color:#cf8bf6;");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.color(1, 1, 1, 1));


        ImageView imageView = new ImageView();
        //Setting image to the image view
        imageView.setImage(new Image(new File(file.getPath()).toURI().toString()));
        //Setting the image view parameters
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        hBox.getChildren().add(textFlow);
        hBox.getChildren().add(imageView);

        vBOxMessage.getChildren().add(hBox);


    }
}
