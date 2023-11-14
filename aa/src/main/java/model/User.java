package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    public static User loggedInUser;
    private String username;
    private String password;
    private int score;
    private int time;
    private URL imageURL;

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }

    public static User getUserByUsername(String username) throws Exception {
        for (User user : getUsersFromJsonFile()) {
            if (Objects.equals(user.getUsername(), username))
                return user;
        }
        return null;
    }

    public static List<User> getUsersFromJsonFile() throws Exception {
        List<User> users = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader("users.json"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();


        JSONArray jsonArray = new JSONArray(sb.toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            User loadedUser = gson.fromJson(String.valueOf(jsonObject), User.class);
            users.add(loadedUser);
        }
        return users;
    }

    public static void updateUserInJsonFile(String newField, String fieldType, String filename, URL url) throws IOException {

        // read the existing contents of the users.json file into a JSONArray
        String jsonString = new String(Files.readAllBytes(Paths.get(filename)));
        JSONArray usersArray = new JSONArray(jsonString);

        // find the index of the JSONObject for the updated User
        int userIndex = -1;
        for (int i = 0; i < usersArray.length(); i++) {
            JSONObject jsonObject = usersArray.getJSONObject(i);
            if (jsonObject.getString("username").equals(loggedInUser.getUsername())) {
                userIndex = i;
                break;
            }
        }
        // update the specified user field
        if (userIndex != -1) {
            switch (fieldType) {
                case "username" -> loggedInUser.setUsername(newField);
                case "password" -> loggedInUser.setPassword(newField);
                case "imageURL" -> loggedInUser.setImageURL(url);
                case "time" -> loggedInUser.setTime(Integer.parseInt(newField));
                case "score" -> loggedInUser.setScore(Integer.parseInt(newField));
                default -> {
                    return;
                }
            }

            JSONObject updatedUser = new JSONObject(loggedInUser);
            usersArray.put(userIndex, updatedUser);
        }
        // write the updated contents of the JSONArray back to the users.json file
        Files.write(Paths.get(filename), usersArray.toString().getBytes());
    }

    public static void removeUser(String username, String filename) throws IOException {
        String jsonString = new String(Files.readAllBytes(Paths.get(filename)));
        JSONArray usersArray = new JSONArray(jsonString);
        int userIndex = -1;
        for (int i = 0; i < usersArray.length(); i++) {
            JSONObject jsonObject = usersArray.getJSONObject(i);
            if (jsonObject.getString("username").equals(loggedInUser.getUsername())) {
                userIndex = i;
                break;
            }
        }
        usersArray.remove(userIndex);
        Files.write(Paths.get(filename), usersArray.toString().getBytes());
    }

    public URL getImageURL() {
        return imageURL;
    }

    public void setImageURL(URL imageURL) {
        this.imageURL = imageURL;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordCorrect(String Pass) {
        return Objects.equals(this.password, Pass);
    }

}
