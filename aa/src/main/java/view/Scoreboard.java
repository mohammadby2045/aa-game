package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class Scoreboard extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL url = MainMenu.class.getResource("/FXML/ScoreBoard.fxml");
        Pane gridPane = FXMLLoader.load(url);
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
    }
}
