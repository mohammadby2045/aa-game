package controller;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Ball;
import view.Game;
import view.LoginMenu;

public class PauseMenuController {
    public static Stage stage;

    public void back(MouseEvent mouseEvent) throws Exception {
        GameController.game.start(LoginMenu.stage);
    }

    public void mute(MouseEvent mouseEvent) {
        LoginMenu.mediaPlayer.setMute(!LoginMenu.mediaPlayer.isMute());
    }

    public void firstMusic(MouseEvent mouseEvent) {
        LoginMenu.media = new Media(getClass().getResource("/sound/02.mp3").toString());
        LoginMenu.mediaPlayer.pause();
        LoginMenu.mediaPlayer = new MediaPlayer(LoginMenu.media);
        LoginMenu.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        LoginMenu.mediaPlayer.setAutoPlay(true);
    }

    public void secondMusic(MouseEvent mouseEvent) {
        LoginMenu.media = new Media(getClass().getResource("/sound/03.mp3").toString());
        LoginMenu.mediaPlayer.pause();
        LoginMenu.mediaPlayer = new MediaPlayer(LoginMenu.media);
        LoginMenu.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        LoginMenu.mediaPlayer.setAutoPlay(true);
    }

    public void thirdMusic(MouseEvent mouseEvent) {
        LoginMenu.media = new Media(getClass().getResource("/sound/01.mp3").toString());
        LoginMenu.mediaPlayer.pause();
        LoginMenu.mediaPlayer = new MediaPlayer(LoginMenu.media);
        LoginMenu.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        LoginMenu.mediaPlayer.setAutoPlay(true);
    }

    public void exit(MouseEvent mouseEvent) {
        Platform.exit();
    }

    public void backGame(MouseEvent mouseEvent) {
        for (int i = 0; i < Game.animations.size(); i++) {
            Game.animations.get(i).play();

        }
        for (int i = 0; i < Game.timelines.size(); i++) {
            Game.timelines.get(i).play();
        }
        PauseMenuController.stage.close();
    }

    public void restart(MouseEvent mouseEvent) throws Exception {
        PauseMenuController.stage.close();
        for (Timeline timeline : Game.timelines) {
            timeline.stop();
        }
        for (Animation animation : Game.animations) {
            animation.stop();
        }
        Ball.balls.clear();
        Game.animations.clear();
        Game.timelines.clear();
        new view.Game().start(LoginMenu.stage);

    }
}
