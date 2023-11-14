package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import view.LoginMenu;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class RegisterMenuController {
    public PasswordField password;
    @FXML
    private TextField username;

    public static void writeInJsonFile(String username, String password, String fileName) throws IOException {
        File file = new File(fileName);

        Map<String, Object> newUserMap = new LinkedHashMap<>();
        newUserMap.put("username", username);
        newUserMap.put("password", password);
        newUserMap.put("score", 0);
        newUserMap.put("time", 0);
        Random rand = new Random();
        int randNum = rand.nextInt(4);
        if (randNum == 0) {
            newUserMap.put("imageURL", LoginMenu.class.getResource("/images/avatar1.jpg"));
        } else if (randNum == 1) {
            newUserMap.put("imageURL", LoginMenu.class.getResource("/images/avatar2.jpg"));
        } else if (randNum == 2) {
            newUserMap.put("imageURL", LoginMenu.class.getResource("/images/avatar3.png"));
        } else {
            newUserMap.put("imageURL", LoginMenu.class.getResource("/images/avatar4.jpg"));
        }
        JSONObject newUser = new JSONObject(newUserMap);

        // read the existing contents of the users.json file into a string
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        // manually add an opening square bracket if the string is empty
        if (sb.length() == 0) {
            sb.append("[]");
        }

        // parse the contents of the file as a JSONArray
        JSONArray usersArray = new JSONArray(sb.toString());

        // add the new user JSONObject to the JSONArray
        usersArray.put(newUser);

        // write the updated contents of the JSONArray back to the users.json file
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(usersArray.toString());
        bw.close();

    }

    public void back(MouseEvent mouseEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("back");
        alert.setHeaderText("back to Login Menu");
        alert.show();
        new LoginMenu().start(LoginMenu.stage);
    }

    public void Reset(MouseEvent mouseEvent) {
        username.setText("");
        password.setText("");
    }

    public void Register(MouseEvent mouseEvent) throws Exception {
        if (password.getText().length() < 4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("your password is weak");
            alert.show();
        } else if (User.getUserByUsername(username.getText()) != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("This username is already taken!");
            alert.show();
        } else {
            Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
            alert.setTitle("register");
            alert.setHeaderText("registered successfully");
            alert.show();
            writeInJsonFile(username.getText(), password.getText(), "users.json");
        }
    }

}
