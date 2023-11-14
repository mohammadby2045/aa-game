package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Canon extends Rectangle {
    public Canon(int x, int y) {
        super(x, y, 66, 50);
        this.setFill(new ImagePattern(
                new Image(Canon.class.getResource("/images/player.png").toExternalForm())));
    }
}
