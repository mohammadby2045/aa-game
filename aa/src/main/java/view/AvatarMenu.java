package view;

import controller.ProfileMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

import java.net.URL;

public class AvatarMenu extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL url = MainMenu.class.getResource("/FXML/AvatarMenu.fxml");
        Pane Pane = FXMLLoader.load(url);
        Scene scene = new Scene(Pane);
        stage.setScene(scene);
        stage.show();
    }
}

