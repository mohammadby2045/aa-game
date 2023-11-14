package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.User;
import view.AvatarMenu;
import view.LoginMenu;
import view.MainMenu;

public class ProfileMenuController {
    @FXML
    public static ImageView image;
    @FXML
    private PasswordField oldPass;
    @FXML
    private TextField username;
    @FXML
    private PasswordField newPass;

    public void back(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(LoginMenu.stage);
    }

    public void changePassword(MouseEvent mouseEvent) throws Exception {
        if (!User.getUserByUsername(User.loggedInUser.getUsername()).getPassword().equals(oldPass.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("changePassword error");
            alert.setContentText("oldPassword is not correct!");
            alert.show();
        } else if (newPass.getText().length() < 4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("changePassword error");
            alert.setContentText("newPassword is weak");
            alert.show();
        } else {
            User.updateUserInJsonFile(newPass.getText(), "password", "users.json",null);
            Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
            alert.setTitle("changePassword");
            alert.setHeaderText("Password changed successfully!");
            alert.show();
        }
    }

    public void deleteAccount(MouseEvent mouseEvent) throws Exception {
        User.removeUser(username.getText(), "users.json");
        Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
        alert.setTitle("deleteUser");
        alert.setHeaderText("User deleted successfully!");
        alert.show();
        new LoginMenu().start(LoginMenu.stage);
    }

    public void changeUsername(MouseEvent mouseEvent) throws Exception {
        if (User.getUserByUsername(username.getText()) != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("this username has already taken!");
            alert.show();
        } else {
            User.updateUserInJsonFile(username.getText(), "username", "users.json",null);
            Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
            alert.setTitle("changeUsername");
            alert.setHeaderText("username changed successfully!");
            alert.show();
        }
    }

    public void logout(MouseEvent mouseEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("logout");
        alert.setHeaderText("logged out");
        alert.show();
        User.loggedInUser = null;
        new LoginMenu().start(LoginMenu.stage);
    }

    public void chooseAvatar(MouseEvent mouseEvent) throws Exception {
        new AvatarMenu().start(LoginMenu.stage);
    }
}
