package controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import model.User;
import view.*;

public class MainMenuController {
    public void newGame(MouseEvent mouseEvent) throws Exception {
        Game.gameController = new GameController();
        Game.numberOfBalls = model.Game.numberOfBalls;
        model.Game.isMultiplay = false;
        new ChooseMapMenu().start(LoginMenu.stage);
    }

    public void resumeGame(MouseEvent mouseEvent) throws Exception {
        Game.gameController = new GameController();
        Game.numberOfBalls = model.Game.numberOfBalls;
        model.Game.isMultiplay = true;
        new ChooseMapMenu().start(LoginMenu.stage);
    }

    public void profileMenu(MouseEvent mouseEvent) throws Exception {
        if (User.loggedInUser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("you don't have any profile!");
            alert.show();
        } else {
            new ProfileMenu().start(LoginMenu.stage);
        }
    }

    public void settings(MouseEvent mouseEvent) throws Exception {
        new Settings().start(LoginMenu.stage);
    }

    public void scoreboard(MouseEvent mouseEvent) throws Exception {
        new Scoreboard().start(LoginMenu.stage);
    }

    public void exit(MouseEvent mouseEvent) {
        Platform.exit();
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("back");
        alert.setHeaderText("back to Login Menu");
        alert.show();
        User.loggedInUser = null;
        new LoginMenu().start(LoginMenu.stage);
    }
}
