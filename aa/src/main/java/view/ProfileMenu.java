package view;

import controller.ProfileMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

import java.net.URL;

public class ProfileMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Image image = new Image(User.loggedInUser.getImageURL().toExternalForm());
        ProfileMenuController.image = new ImageView();
        ProfileMenuController.image.setImage(image);
        URL url = MainMenu.class.getResource("/FXML/ProfileMenu.fxml");
        Pane Pane = FXMLLoader.load(url);
        Scene scene = new Scene(Pane);
        ProfileMenuController.image.setFitWidth(120.0);
        ProfileMenuController.image.setFitHeight(68.0);
        ProfileMenuController.image.setX(241.0);
        ProfileMenuController.image.setY(44.0);
        Pane.getChildren().add(ProfileMenuController.image);
        stage.setScene(scene);
        stage.show();
    }
}
