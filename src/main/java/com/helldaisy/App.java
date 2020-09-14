package com.helldaisy;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.*;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("FXML TableView Example");
        Parent root  = FXMLLoader.load(getClass().getResource("/main.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}