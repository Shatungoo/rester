package com.helldaisy;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.*;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Rester");
        Parent root  = FXMLLoader.load(getClass().getResource("/main.fxml"));
        var scene = new Scene(root);
        stage.setScene(scene);
        // root.getStylesheets().add("/themes/night.css");
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene); 
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}