package com.helldaisy.controllers;

import java.io.IOException;

import com.helldaisy.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TitleController {

    private double xOffset = 0;
    private double yOffset = 0;

    public Main main;

    @FXML
    public void initialize() {
        
    }

    @FXML
    public void close(final ActionEvent event) {
        main.close();
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void minimize(final ActionEvent event) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void maximize(final ActionEvent event) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        stage.setMaximized(!stage.isMaximized());
    }

    @FXML
    public void mousePress(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();

    }

    @FXML
    public void mouseDrag(MouseEvent event) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);

    }

    @FXML
    public void openImport(final ActionEvent event){
        System.out.println("openImport");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/import.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Import");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openSetings(final ActionEvent event){
        System.out.println("openImport");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/settings.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
