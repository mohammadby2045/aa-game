package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Game;
import view.LoginMenu;
import view.MainMenu;

public class SettingsMenuController {
    @FXML
    public TextField numberOfBalls;

    public void back(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(LoginMenu.stage);
    }

    public void mute(MouseEvent mouseEvent) {
        LoginMenu.mediaPlayer.setMute(!LoginMenu.mediaPlayer.isMute());
    }

    public void firstGameMode(MouseEvent mouseEvent) {
        Game.difficultyLevel = 1;
        Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
        alert.setTitle("SetDifficultyLevel");
        alert.setHeaderText("Difficulty level changed successfully!");
        alert.show();
    }

    public void secondGameMode(MouseEvent mouseEvent) {
        Game.difficultyLevel = 2;
        Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
        alert.setTitle("SetDifficultyLevel");
        alert.setHeaderText("Difficulty level changed successfully!");
        alert.show();
    }

    public void thirdGameMode(MouseEvent mouseEvent) {
        Game.difficultyLevel = 3;
        Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
        alert.setTitle("SetDifficultyLevel");
        alert.setHeaderText("Difficulty level changed successfully!");
        alert.show();
    }

    public void submit(MouseEvent mouseEvent) {
        if (!numberOfBalls.getText().matches("\\d+")) {
            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.setTitle("invalid number");
            alert.setHeaderText("Invalid Number For Balls!");
            alert.show();
        } else if (Integer.parseInt(numberOfBalls.getText()) < 3) {
            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.setTitle("invalid number");
            alert.setHeaderText("Number Of Balls Should be more than 2");
            alert.show();
        } else {
            Game.numberOfBalls = Integer.parseInt(numberOfBalls.getText());
            Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
            alert.setTitle("Number Of Balls");
            alert.setHeaderText("Number Of Balls Changed Successfully!");
            alert.show();
        }
    }
}
