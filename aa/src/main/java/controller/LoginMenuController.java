package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.User;
import view.LoginMenu;
import view.MainMenu;
import view.RegisterMenu;

public class LoginMenuController {
    @FXML
    private PasswordField password;
    @FXML
    private TextField username;

    public void Login(MouseEvent mouseEvent) throws Exception {
        if (User.getUserByUsername(username.getText()) == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("login error");
            alert.setContentText("Username does not exists!");
            alert.show();
        } else if (!User.getUserByUsername(username.getText()).isPasswordCorrect(password.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("login error");
            alert.setContentText("Password is not correct!");
            alert.show();
        } else {
            User.loggedInUser = User.getUserByUsername(username.getText());
            new MainMenu().start(LoginMenu.stage);
        }
    }

    public void Reset(MouseEvent mouseEvent) {
        username.setText("");
        password.setText("");
    }

    public void Register(MouseEvent mouseEvent) throws Exception {
        new RegisterMenu().start(LoginMenu.stage);
    }

    public void playGuest(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(LoginMenu.stage);
    }

}
