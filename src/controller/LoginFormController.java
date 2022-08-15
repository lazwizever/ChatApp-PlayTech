package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class LoginFormController {

    public TextField txtUserName;
    public AnchorPane logInFormContext;


    public void loginOnAction(ActionEvent actionEvent) throws IOException {
            ChattingFormController.userName = txtUserName.getText();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/ChattingForm.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) logInFormContext.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }
}

