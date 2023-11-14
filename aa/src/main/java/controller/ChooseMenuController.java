package controller;

import javafx.scene.input.MouseEvent;
import model.Game;
import view.LoginMenu;
import view.MainMenu;

public class ChooseMenuController {
    public void back(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(LoginMenu.stage);
    }

    public void map1(MouseEvent mouseEvent) throws Exception {
        Game.numberOFStartingBalls = 4;
        new view.Game().start(LoginMenu.stage);
    }

    public void map2(MouseEvent mouseEvent) throws Exception {
        Game.numberOFStartingBalls = 5;
        new view.Game().start(LoginMenu.stage);
    }

    public void map3(MouseEvent mouseEvent) throws Exception {
        Game.numberOFStartingBalls = 6;
        new view.Game().start(LoginMenu.stage);
    }
}
