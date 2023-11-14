package controller;

import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import model.User;
import view.LoginMenu;
import view.ProfileMenu;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class AvatarMenuController {
    public void Avatar1() throws IOException {
        User.updateUserInJsonFile("", "imageURL", "users.json", getClass().getResource("/images/avatar1.jpg"));
    }

    public void Avatar2() throws IOException {
        User.updateUserInJsonFile("", "imageURL", "users.json", getClass().getResource("/images/avatar2.jpg"));
    }

    public void Avatar3() throws IOException {
        User.updateUserInJsonFile("", "imageURL", "users.json", getClass().getResource("/images/avatar3.png"));
    }

    public void Avatar4() throws IOException {
        User.updateUserInJsonFile("", "imageURL", "users.json", getClass().getResource("/images/avatar4.jpg"));
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new ProfileMenu().start(LoginMenu.stage);
    }

    public void chooseFromFiles(MouseEvent mouseEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG Files", "*.jpg")
                , new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(LoginMenu.stage);
        User.updateUserInJsonFile("", "imageURL", "users.json", selectedFile.toURI().toURL());
    }
}
