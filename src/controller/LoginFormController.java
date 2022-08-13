package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public TextField txtUserName;

    String userName = "lasa";

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        if (txtUserName.getText().equals(userName)){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/ChattingForm.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        }


    }
}
