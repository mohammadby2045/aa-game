package view;

import controller.ScoreGameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

import java.net.URL;

public class ScoreGame extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL url = MainMenu.class.getResource("/FXML/ScoreGame.fxml");
        ScoreGameController.score = new Text();
        ScoreGameController.score.setText("" + (Game.score.getText()));
        ScoreGameController.time = new Text();
        int time = (model.Game.TimeEnd.getMinute() - model.Game.TimeStart.getMinute()) * 60;
        time += model.Game.TimeEnd.getSecond() - model.Game.TimeStart.getSecond();
        if (User.loggedInUser != null) {
            int Time = User.getUserByUsername(User.loggedInUser.getUsername()).getTime();
            int score = User.getUserByUsername(User.loggedInUser.getUsername()).getScore();
            User.updateUserInJsonFile(String.valueOf(time + Time), "time", "users.json", null);
            User.updateUserInJsonFile(String.valueOf(model.Game.score + score), "score", "users.json", null);
        }
        ScoreGameController.time.setText(time + "  seconds");
        ScoreGameController.diffLevel = new Text();
        ScoreGameController.diffLevel.setText("" + model.Game.difficultyLevel);
        Pane gridPane = FXMLLoader.load(url);
        ScoreGameController.score.setLayoutX(50);
        ScoreGameController.score.setLayoutY(95);
        ScoreGameController.score.setFont(Font.font("Verdana", FontPosture.REGULAR, 20));
        ScoreGameController.score.setFill(Color.WHITE);
        ScoreGameController.time.setLayoutX(150);
        ScoreGameController.time.setLayoutY(255);
        ScoreGameController.time.setFont(Font.font("Verdana", FontPosture.REGULAR, 20));
        ScoreGameController.time.setFill(Color.WHITE);
        ScoreGameController.diffLevel.setLayoutX(180);
        ScoreGameController.diffLevel.setLayoutY(175);
        ScoreGameController.diffLevel.setFont(Font.font("Verdana", FontPosture.REGULAR, 20));
        ScoreGameController.diffLevel.setFill(Color.WHITE);
        gridPane.getChildren().add(ScoreGameController.score);
        gridPane.getChildren().add(ScoreGameController.time);
        gridPane.getChildren().add(ScoreGameController.diffLevel);
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
    }
}
