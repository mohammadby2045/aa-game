module workshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;
    requires javafx.media;

    exports view;
    exports controller;
    opens controller to javafx.fxml;
    exports model;
    opens model to javafx.fxml,com.google.gson;
    opens view to com.google.gson, javafx.fxml;


}