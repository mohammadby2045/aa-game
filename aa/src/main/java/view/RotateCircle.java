package view;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Ball;

import java.time.LocalTime;
import java.util.Random;

public class RotateCircle extends Transition {

    private Pane pane;

    public RotateCircle(Pane pane) {
        this.pane = pane;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(500));
    }

    @Override
    protected void interpolate(double v) {
        double Teta;
        double deltaTeta;
        if (model.Game.isFreeze) {
            deltaTeta = 0.0025;
        } else {
            if (model.Game.difficultyLevel == 1) {
                deltaTeta = 0.005;
            } else if (model.Game.difficultyLevel == 2) {
                deltaTeta = 0.01;
            } else {
                deltaTeta = 0.015;
            }
        }
        LocalTime myObj = LocalTime.now();
        if (myObj.getSecond() % Game.randomNumber == 0 && Game.time != myObj.getSecond()) {
            Game.time = myObj.getSecond();
            Game.dir = !Game.dir;
            Random rand = new Random();
            Game.randomNumber = rand.nextInt(3) + 4;
        }
        for (int i = 0; i < Ball.balls.size(); i++) {
            Teta = Ball.balls.get(i).getTeta();
            double y = Math.cos(Teta) * Game.gameController.getMainBall().getRadius() * 2;
            double x = Math.sin(Teta) * Game.gameController.getMainBall().getRadius() * 2;
            Ball.getBalls().get(i).setCenterX(Game.gameController.getMainBall().getCenterX() + x);
            Ball.getBalls().get(i).setCenterY(Game.gameController.getMainBall().getCenterY() + y);
            Ball.getBalls().get(i).getLine().setEndY(Game.gameController.getMainBall().getCenterY() + y);
            Ball.getBalls().get(i).getLine().setEndX(Game.gameController.getMainBall().getCenterX() + x);
            if (Ball.getBalls().get(i).getText() != null) {
                Ball.getBalls().get(i).getText().setX(Game.gameController.getMainBall().getCenterX() + x - 5);
                Ball.getBalls().get(i).getText().setY(Game.gameController.getMainBall().getCenterY() + y + 5);
            }
            double x1 = Game.numberOfBalls, x2 = model.Game.numberOfBalls;
            if (Game.dir && (((x1 / x2) * 100.0) <= 75.0)) Ball.getBalls().get(i).setTeta(Teta + deltaTeta);
            else Ball.getBalls().get(i).setTeta(Teta - deltaTeta);
        }
    }
}
