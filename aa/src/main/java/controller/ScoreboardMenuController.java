package controller;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.User;
import view.LoginMenu;
import view.MainMenu;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardMenuController {
    public Label label1;
    public Label label2;
    public Label label3;
    public Label label4;
    public Label label5;
    public Label label6;
    public Label label7;
    public Label label8;
    public Label label9;
    public Label label10;

    public void refresh(MouseEvent mouseEvent) throws Exception {
        List<User> users1 = new ArrayList<>(User.getUsersFromJsonFile());
        User[] users = new User[users1.size()];
        for (int i = 0; i < users1.size(); i++) {
            users[i] = users1.get(i);
        }
        for (int i = 0; i < users.length; i++) {
            for (int j = i + 1; j < users.length; j++) {
                if (users[i].getScore() < users[j].getScore()) {
                    User user = users[i];
                    users[i] = users[j];
                    users[j] = user;
                }
            }
        }
        for (int i = 0; i < users.length; i++) {
            for (int j = i + 1; j < users.length; j++) {
                if (users[i].getScore() == users[j].getScore() && users[i].getTime() > users[j].getTime()) {
                    User user = users[i];
                    users[i] = users[j];
                    users[j] = user;
                }
            }
        }
        for (int i = 0; i < users.length; i++) {
            for (int j = i + 1; j < users.length; j++) {
                int x = users[i].getUsername().compareTo(users[j].getUsername());
                if (users[i].getScore() == users[j].getScore() && users[i].getTime() == users[j].getTime() && x > 0) {
                    User user = users[i];
                    users[i] = users[j];
                    users[j] = user;
                }
            }
        }
        if (users.length > 0) {
            label1.setText("username: " + users[0].getUsername() + " score: " + users[0].getScore() + " time: " + users[0].getTime());
        }
        if (users.length > 1) {
            label2.setText("username: " + users[1].getUsername() + " score: " + users[1].getScore() + " time: " + users[1].getTime());
        }
        if (users.length > 2) {
            label3.setText("username: " + users[2].getUsername() + " score: " + users[2].getScore() + " time: " + users[2].getTime());
        }
        if (users.length > 3) {
            label4.setText("username: " + users[3].getUsername() + " score: " + users[3].getScore() + " time: " + users[3].getTime());
        }
        if (users.length > 4) {
            label5.setText("username: " + users[4].getUsername() + " score: " + users[4].getScore() + " time: " + users[4].getTime());
        }
        if (users.length > 5) {
            label6.setText("username: " + users[5].getUsername() + " score: " + users[5].getScore() + " time: " + users[5].getTime());
        }
        if (users.length > 6) {
            label7.setText("username: " + users[6].getUsername() + " score: " + users[6].getScore() + " time: " + users[6].getTime());
        }
        if (users.length > 7) {
            label8.setText("username: " + users[7].getUsername() + " score: " + users[7].getScore() + " time: " + users[7].getTime());
        }
        if (users.length > 8) {
            label9.setText("username: " + users[8].getUsername() + " score: " + users[8].getScore() + " time: " + users[8].getTime());
        }
        if (users.length > 9) {
            label10.setText("username: " + users[9].getUsername() + " score: " + users[9].getScore() + " time: " + users[9].getTime());
        }
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(LoginMenu.stage);
    }
}
