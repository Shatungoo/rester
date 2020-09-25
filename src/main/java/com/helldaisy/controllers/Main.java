package com.helldaisy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.helldaisy.Utils.Loader;

// import io.swagger.OpenAPIV3Parser;
// import io.swagger.v3.oas.models.OpenAPI;

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

import java.util.Optional;
public class Main {
    @FXML
    public TableView<String> responseHeadersTable;

    final HashMap<String, String> responseHeaders = new HashMap<>();

    @FXML
    public ListView<History> history;

    @FXML
    public AnchorPane title;

    @FXML
    public TextArea responseField;

    @FXML
    public TableColumn<String, String> responseName, responseValue;

    @FXML
    public VBox request;
    @FXML
    public RequestController requestControllerController;

    @FXML
    public TreeView<CollectionFX> collections;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    public void initialize() {
        var settings = Optional
            .ofNullable(Loader.load())
            .orElse(new ArrayList<History> ());
        history.getItems().addAll(settings);

        requestControllerController.main = this;

        var germanics = new TreeItem<CollectionFX>(new CollectionFX(new Collection()));
        // germanics.getChildren().add(new TreeItem<String>("German"));

        collections.setRoot(germanics);
        // OpenAPI oa = new OpenAPIV3Parser().read("");
        


        responseHeadersTable.getItems().addAll(responseHeaders.keySet());
        responseName.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()));
        responseValue.setCellValueFactory(cd -> new SimpleStringProperty(responseHeaders.get(cd.getValue())));
        responseName.setCellFactory(TextFieldTableCell.forTableColumn());
        responseValue.setCellFactory(TextFieldTableCell.forTableColumn());

        history.setCellFactory(history -> new ListCell<History>() {
            @Override
            protected void updateItem(final History history, final boolean empty) {
                super.updateItem(history, empty);
                if (history != null) {
                    final var request = history.request;
                    setText(request.method + " " + request.URL);
                } else {
                    setText("");
                }
            }
        });
    }

    @FXML
    public void historySelection(final MouseEvent event) {
        var request = history.getSelectionModel().getSelectedItem().request;
        var response = history.getSelectionModel().getSelectedItem().response;
        requestControllerController.setRequest (request);
        setResponce(response);
    }

    public void setResponce(Response response) {
        responseHeaders.clear();
        response.headers.forEach((key, values) -> responseHeaders.put(key, values.toString()));
        responseHeadersTable.getItems().setAll(responseHeaders.keySet());
        responseField.setText(response.body);
    }

    @FXML
    public void close(final ActionEvent event) {
        Loader.save(new ArrayList(history.getItems()));
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
