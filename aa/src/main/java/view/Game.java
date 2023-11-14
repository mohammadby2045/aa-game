package view;

import controller.GameController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Ball;
import model.Canon;
import model.MainBall;

import java.time.LocalTime;
import java.util.ArrayList;

public class Game extends Application {
    public static int numberOfBalls;
    public static GameController gameController;
    public static int time;
    public static boolean dir;
    public static int randomNumber;
    public static ArrayList<Animation> animations = new ArrayList<>();
    public static ArrayList<Timeline> timelines = new ArrayList<>();
    public static Text score;
    public Label remain;
    public ProgressBar progressBar;

    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }

    public static void loseTheGame(Pane pane) throws Exception {
        model.Game.TimeEnd = LocalTime.now();
        for (int i = 0; i < Ball.balls.size(); i++) {
            Ball.balls.get(i).setVisible(true);
            Ball.balls.get(i).getLine().setVisible(true);
            if (Ball.balls.get(i).getText() != null) Ball.balls.get(i).getText().setVisible(true);
        }
        Ball.balls.clear();
        for (Timeline timeline : timelines) {
            timeline.stop();
        }
        for (Animation animation : animations) {
            animation.stop();
        }
        animations.clear();
        timelines.clear();
        pane.getChildren().get(0).requestFocus();
        Background background = new Background(setBackGround(0));
        pane.setBackground(background);
        delay(3000, () -> {
            try {
                gameController.back();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static BackgroundImage setBackGround(int num) {
        Image image = null;
        if (num == 0) {
            image = new Image(Game.class.getResource("/images/back.jpg").toExternalForm(), 800, 600, false, false);
        } else {
            image = new Image(Game.class.getResource("/images/back2.jpg").toExternalForm(), 800, 600, false, false);
        }
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        return backgroundImage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        GameController.game = this;
        Pane gamePane = FXMLLoader.load(Game.class.getResource("/fxml/game.fxml"));
        MainBall mainBall = new MainBall();
        gamePane.getChildren().add(mainBall);
        view.Game.gameController.setMainBall(mainBall);
        HBox hBox = new HBox();
        ProgressBar progressBar = new ProgressBar(0);
        this.progressBar = progressBar;
        createHbox(hBox, progressBar);
        gamePane.getChildren().add(hBox);
        createCanon(gamePane, progressBar, mainBall);
        Scene scene = new Scene(gamePane);
        stage.setScene(scene);
        numberOfBalls = model.Game.numberOfBalls;
        dir = false;
        time = LocalTime.now().getSecond();
        randomNumber = 4;
        model.Game.TimeStart = LocalTime.now();
        gamePane.getChildren().get(2).requestFocus();
        if (model.Game.isMultiplay) {
            ImageView imageView = new ImageView(new Image(Canon.class.getResource("/images/rocket.png").toExternalForm()));
            imageView.setFitHeight(60);
            imageView.setFitWidth(50);
            imageView.setX(368);
            imageView.setY(10);
            gamePane.getChildren().add(imageView);
        }
        remain = new Label("" + numberOfBalls);
        remain.setLayoutX(397);
        remain.setLayoutY(580);
        remain.setTextFill(Color.RED);
        remain.setFont(Font.font("Verdana", FontPosture.REGULAR, 10));
        gamePane.getChildren().add(remain);
        score = new Text("score: " + (model.Game.numberOfBalls - numberOfBalls) * model.Game.difficultyLevel);
        score.setLayoutY(mainBall.getCenterY());
        score.setLayoutX(mainBall.getCenterX() + 320);
        score.setFont(Font.font("Verdana", FontPosture.REGULAR, 20));
        score.setFill(Color.PURPLE);
        gamePane.getChildren().add(score);
        setBallForStart(model.Game.numberOFStartingBalls, gamePane);
        RotateCircle rotateCircle = new RotateCircle(gamePane);
        animations.add(rotateCircle);
        rotateCircle.play();
        setTimeLineBig(gamePane);
        setTimeLineVisible(gamePane);
        stage.show();
    }

    private void setBallForStart(int num, Pane pane) {
        int deg = 360 / num;
        for (int i = 0; i < num; i++) {
            Ball ball = new Ball(0, 0);
            ball.setTeta(Math.toRadians(deg * i));
            pane.getChildren().add(ball);
            Ball.balls.add(ball);
            Line line = ball.getLine();
            line.setStartX(view.Game.gameController.getMainBall().getCenterX());
            line.setStartY(view.Game.gameController.getMainBall().getCenterY());
            pane.getChildren().add(ball.getLine());
        }
    }

    private boolean isIntersects(Ball ball, Ball ball2) {
        double a = ball.getRadius() + ball2.getRadius();
        double b = Math.sqrt(Math.pow(ball.getCenterX() - ball2.getCenterX(), 2) + Math.pow(ball.getCenterY() - ball2.getCenterY(), 2));
        return a >= b;
    }

    private void setTimeLineBig(Pane pane) {
        Timeline timeline = new Timeline();
        timelines.add(timeline);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (int i = 0; i < Ball.balls.size(); i++) {
                    double x1 = numberOfBalls, x2 = model.Game.numberOfBalls;
                    if (((x1 / x2) * 100.0) <= 75.0) {
                        if (Ball.balls.get(0).isBig()) {
                            Ball.balls.get(i).setRadius(6);
                        } else {
                            Ball.balls.get(i).setRadius(10);
                        }
                    }
                    for (int j = 0; j < Ball.balls.size(); j++) {
                        if (i != j && isIntersects(Ball.balls.get(j), Ball.balls.get(i))) {
                            try {
                                loseTheGame(pane);
                                timeline.stop();
                                return;
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
                score.setText("score: " + (Ball.balls.size() - model.Game.numberOFStartingBalls) * model.Game.difficultyLevel);
                model.Game.score = (Ball.balls.size() - model.Game.numberOFStartingBalls) * model.Game.difficultyLevel;
                if (numberOfBalls <= 0) {
                    try {
                        winTheGame(pane);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
                if (Ball.balls.size() > 0) {
                    Ball.balls.get(0).setBig(!Ball.balls.get(0).isBig());
                }
            }
        }));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void setTimeLineVisible(Pane pane) {
        Timeline timeline = new Timeline();
        timelines.add(timeline);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.8), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean bool = false;
                if (Ball.balls.size() > 0) {
                    bool = Ball.balls.get(0).isVisible();
                }
                for (int i = 0; i < Ball.balls.size(); i++) {
                    double x1 = numberOfBalls, x2 = model.Game.numberOfBalls;
                    if (((x1 / x2) * 100.0) <= 50.0) {
                        if (bool) {
                            Ball.getBalls().get(i).setVisible(false);
                            Ball.balls.get(i).getLine().setVisible(false);
                            if (Ball.balls.get(i).getText() != null) Ball.balls.get(i).getText().setVisible(false);
                        } else {
                            Ball.balls.get(i).setVisible(true);
                            Ball.balls.get(i).getLine().setVisible(true);
                            if (Ball.balls.get(i).getText() != null) Ball.balls.get(i).getText().setVisible(true);
                        }
                    }
                }
            }
        }));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void createCanon(Pane gamePane, ProgressBar progressBar, MainBall mainBall) {
        Canon rocket = new Canon(367, 530);
        rocket.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName = keyEvent.getCode().getName();
                switch (keyName) {
                    case "Tab" -> {
                        if (progressBar.getProgress() >= 1) {
                            freeze(mainBall);
                        }
                    }
                    case "P" -> {
                        try {
                            gameController.pause();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "Left" -> {
                        double x1 = numberOfBalls, x2 = model.Game.numberOfBalls;
                        if (((x1 / x2) * 100.0) <= 25.0) {
                            Game.gameController.moveLeft(rocket);
                        }
                    }
                    case "Right" -> {
                        double x1 = numberOfBalls, x2 = model.Game.numberOfBalls;
                        if (((x1 / x2) * 100.0) <= 25.0) {
                            Game.gameController.moveRight(rocket);
                        }
                    }
                    case "Space" -> {
                        if (numberOfBalls > 0) {
                            Game.gameController.shoot(rocket, gamePane, progressBar, true);
                            remain.setText("" + (numberOfBalls - 1));
                            double x1 = numberOfBalls - 1, x2 = model.Game.numberOfBalls;
                            if ((x1 / x2) * 100 <= 50 && (x1 / x2) * 100 > 25) {
                                remain.setTextFill(Color.PURPLE);
                            } else if ((x1 / x2) * 100 <= 25) {
                                remain.setTextFill(Color.GREEN);
                            }
                        }
                    }
                    case "Enter" -> {
                        if (model.Game.isMultiplay && numberOfBalls > 0) {
                            Game.gameController.shoot(rocket, gamePane, progressBar, false);
                            remain.setText("" + (numberOfBalls - 1));
                            double x1 = numberOfBalls - 1, x2 = model.Game.numberOfBalls;
                            if ((x1 / x2) * 100 <= 50 && (x1 / x2) * 100 > 25) {
                                remain.setTextFill(Color.PURPLE);
                            } else if ((x1 / x2) * 100 <= 25) {
                                remain.setTextFill(Color.GREEN);
                            }
                        }
                    }
                }
            }
        });
        gamePane.getChildren().add(rocket);
    }

    private void freeze(MainBall mainBall) {
        mainBall.setFill(new ImagePattern(new Image(
                getClass().getResource("/images/ice" + 1 + ".jpg").toExternalForm())));
        model.Game.isFreeze = true;
        this.progressBar.setProgress(0);
        int delay;
        if (model.Game.difficultyLevel == 1) {
            delay = 7000;
        } else if (model.Game.difficultyLevel == 2) {
            delay = 5000;
        } else {
            delay = 3000;
        }
        delay((delay / 2), () -> {
            mainBall.setFill(new ImagePattern(new Image(
                    getClass().getResource("/images/ice" + 2 + ".jpg").toExternalForm())));
        });
        delay(delay, () -> {
            try {
                model.Game.isFreeze = false;
                mainBall.setFill(Color.BLACK);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void winTheGame(Pane gamePane) throws Exception {
        model.Game.TimeEnd = LocalTime.now();
        for (Timeline timeline : timelines) {
            timeline.stop();
        }
        for (Animation animation : animations) {
            animation.stop();
        }
        double Teta;
        for (int i = 0; i < Ball.balls.size(); i++) {
            Ball.balls.get(i).setVisible(true);
            Ball.balls.get(i).getLine().setVisible(true);
            if (Ball.balls.get(i).getText() != null) Ball.balls.get(i).getText().setVisible(true);
            Teta = Ball.balls.get(i).getTeta();
            double y = Math.cos(Teta) * Game.gameController.getMainBall().getRadius() * 3.2;
            double x = Math.sin(Teta) * Game.gameController.getMainBall().getRadius() * 3.2;
            Ball.getBalls().get(i).setCenterX(Game.gameController.getMainBall().getCenterX() + x);
            Ball.getBalls().get(i).setCenterY(Game.gameController.getMainBall().getCenterY() + y);
            Ball.getBalls().get(i).getLine().setEndY(Game.gameController.getMainBall().getCenterY() + y);
            Ball.getBalls().get(i).getLine().setEndX(Game.gameController.getMainBall().getCenterX() + x);
            if (Ball.getBalls().get(i).getText() != null) {
                Ball.getBalls().get(i).getText().setX(Game.gameController.getMainBall().getCenterX() + x - 5);
                Ball.getBalls().get(i).getText().setY(Game.gameController.getMainBall().getCenterY() + y + 5);
            }
        }
        Ball.balls.clear();
        animations.clear();
        timelines.clear();
        gamePane.getChildren().get(0).requestFocus();
        Background background = new Background(setBackGround(1));
        gamePane.setBackground(background);
        delay(3000, () -> {
            try {
                gameController.back();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void createHbox(HBox hBox, ProgressBar progressBar) {
        hBox.setAlignment(Pos.CENTER);
        hBox.setLayoutX(0);
        hBox.setLayoutY(250);
        hBox.setSpacing(50);
        hBox.getChildren().add(progressBar);
    }
}
