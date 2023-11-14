package view;

import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Ball;
import model.Canon;
import model.MainBall;

public class ShootingAnimation extends Transition {
    private Pane pane;
    private Canon rocket;
    private Ball laser;
    private ProgressBar progressBar;

    public ShootingAnimation(Pane pane, Canon rocket, Ball laser, ProgressBar progressBar) {
        this.pane = pane;
        this.rocket = rocket;
        this.laser = laser;
        this.progressBar = progressBar;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double y = laser.getCenterY() - 4;
        MainBall meteorite = view.Game.gameController.getMainBall();
        if (meteorite != null && meteorite.getBoundsInParent().intersects(laser.getLayoutBounds())) {
            collision();
            Media media = new Media(getClass().getResource("/sound/shot.m4a").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            pane.getChildren().remove(laser);
            Ball ball = new Ball((int) laser.getCenterX(), (int) (laser.getCenterY() + 100));

            ball.setFill(new ImagePattern(new Image(
                    getClass().getResource("/images/fire.jpg").toExternalForm())));
            Game.delay(1500, () -> {
                try {
                    ball.setFill(Color.BLUE);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            setBallWithLine(ball);
            double teta;
            double y2 = Math.sqrt(Game.gameController.getMainBall().getRadius() * Game.gameController.getMainBall().getRadius() - (ball.getCenterX() - Game.gameController.getMainBall().getCenterX()) * (ball.getCenterX() - Game.gameController.getMainBall().getCenterX()));
            double x2 = (ball.getCenterX() - Game.gameController.getMainBall().getCenterX()) * Game.gameController.getMainBall().getRadius() / (Game.gameController.getMainBall().getRadius() + ball.getRadius());
            teta = Math.atan(x2 / (y2));
            if (laser.getTeta() == 3.14) {
                ball.setTeta(3.14);
            } else {
                ball.setTeta(teta);
            }
            RotateTransition transition = new RotateTransition();
            transition.setNode(rocket);
            transition.setDuration(Duration.millis(1000));
            transition.setFromAngle(0);
            transition.setToAngle(360);
            transition.play();
            ball.setText(new Text("" + view.Game.numberOfBalls));
            view.Game.numberOfBalls -= 1;
            ball.getText().setY(ball.getCenterY() - 2);
            ball.getText().setX(ball.getCenterX());
            ball.getText().setFont(Font.font("Verdana", FontPosture.REGULAR, 10));
            ball.getText().setFill(Color.WHITE);
            pane.getChildren().add(ball);
            pane.getChildren().add(ball.getText());
            Ball.balls.add(ball);
            this.stop();
        }


        if (y <= 20) {
            pane.getChildren().remove(laser);
            this.stop();
            try {
                Game.loseTheGame(pane);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        laser.setCenterY(y);
    }

    private void setBallWithLine(Ball ball) {
        Line line = ball.getLine();
        line.setStartX(view.Game.gameController.getMainBall().getCenterX());
        line.setEndX(laser.getCenterX());
        line.setStartY(view.Game.gameController.getMainBall().getCenterY());
        line.setEndY(laser.getCenterY() + 100);
        pane.getChildren().add(line);
    }

    private void collision() {
        progressBar.setProgress(progressBar.getProgress() + 0.25);
    }
}
