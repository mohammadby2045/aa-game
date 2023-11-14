package model;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Ball extends Circle {
    public static ArrayList<Ball> balls = new ArrayList<>();
    private Line line;
    private double teta;
    private boolean isBig;
    private Text text;

    public Ball(Canon rocket) {
        super(rocket.getX() + 31, rocket.getY() - 10, 6);
        line = new Line();
        isBig = true;
    }

    public Ball(int x, int y) {
        this.setCenterY(y);
        this.setCenterX(x);
        this.setRadius(6);
        line = new Line();
    }

    public static ArrayList<Ball> getBalls() {
        return balls;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public boolean isBig() {
        return isBig;
    }

    public void setBig(boolean big) {
        isBig = big;
    }

    public double getTeta() {
        return teta;
    }

    public void setTeta(double teta) {
        this.teta = teta;
    }

    public Line getLine() {
        return line;
    }

}
