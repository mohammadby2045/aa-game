package controller;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Ball;
import model.Canon;
import model.MainBall;
import view.*;

public class GameController {

    public static Game game;
    private MainBall mainBall;

    public MainBall getMainBall() {
        return mainBall;
    }

    public void setMainBall(MainBall mainBall) {
        this.mainBall = mainBall;
    }

    public void moveLeft(Canon canon) {
        if (canon.getX() > 20)
            canon.setX(canon.getX() - 10);
    }

    public void moveRight(Canon canon) {
        if (canon.getX() < 718)
            canon.setX(canon.getX() + 10);
    }

    public void shoot(Canon rocket, Pane gamePane, ProgressBar progressBar, Boolean fromdown) {
        Ball laser = new Ball(rocket);
        if (!fromdown) {
            laser.setTeta(3.14);
            laser.setCenterY(33);
        }
        gamePane.getChildren().add(laser);
        if (!fromdown) {
            ShootingAnimationUp shootingAnimationUp = new ShootingAnimationUp(gamePane, rocket, laser, progressBar);
            shootingAnimationUp.play();
        } else {
            ShootingAnimation shootingAnimation = new ShootingAnimation(gamePane, rocket, laser, progressBar);
            shootingAnimation.play();
        }
    }

    public void back() throws Exception {
        new ScoreGame().start(LoginMenu.stage);
    }

    public void pause() throws Exception {
        for (int i = 0; i < Game.animations.size(); i++) {
            Game.animations.get(i).pause();

        }
        for (int i = 0; i < Game.timelines.size(); i++) {
            Game.timelines.get(i).pause();
        }
        PauseMenuController.stage = new Stage();
        new PauseMenu().start(PauseMenuController.stage);
    }


}
