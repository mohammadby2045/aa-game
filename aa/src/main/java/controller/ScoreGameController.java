package controller;

import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import view.LoginMenu;
import view.MainMenu;

public class ScoreGameController {
    public static Text score;
    public static Text diffLevel;
    public static Text time;

    public void back(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(LoginMenu.stage);
    }

    public void exit(MouseEvent mouseEvent) {
        Platform.exit();
    }
}
