package com.helldaisy;

import java.util.HashMap;

import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController {

    @FXML
    public TextField urlField;

    @FXML
    public TextArea responseField;

    @FXML
    public ChoiceBox<String> method;

    @FXML
    public TextArea requestBody;

    @FXML
    public TableView<String> requestHeadersTable;

    final HashMap<String, String> requestHeaders = new HashMap<>();

    @FXML
    public TableView<String> responseHeadersTable;

    final HashMap<String, String> responseHeaders = new HashMap<>();

    @FXML
    public ListView<History> history;

    @FXML
    public AnchorPane title;

    @FXML
    public TableColumn<String, String> requestName, requestValue, responseName, responseValue; 

    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    public void initialize() {
        // Deafult method
        method.getItems().addAll("GET", "POST", "PUT", "DELETE","PATCH");
        method.getSelectionModel().select("GET");

        title.setOnMousePressed(new EventHandler<MouseEvent>() { 
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        requestHeaders.put("", "");
        requestHeadersTable.getItems().addAll(requestHeaders.keySet());
        requestName.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()));
        requestValue.setCellValueFactory(cd -> new SimpleStringProperty(requestHeaders.get(cd.getValue())));
        requestHeadersTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        requestName.setCellFactory(TextFieldTableCell.forTableColumn());
        requestValue.setCellFactory(TextFieldTableCell.forTableColumn());


        responseHeadersTable.getItems().addAll(responseHeaders.keySet());
        responseName.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()));
        responseValue.setCellValueFactory(cd -> new SimpleStringProperty(responseHeaders.get(cd.getValue())));
        responseName.setCellFactory(TextFieldTableCell.forTableColumn());
        responseValue.setCellFactory(TextFieldTableCell.forTableColumn());
        requestHeadersTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        title.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                final var source = (Node) event.getSource();
                final var stage = (Stage) source.getScene().getWindow();
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        
        history.setCellFactory(history -> new ListCell<History>(){
            @Override
            protected void updateItem(final History history, final boolean empty) {
            if (empty || history == null) {
                return;
            }
            super.updateItem(history, empty);
            final var request = history.request;
            setText(request.method + " " + request.URL);
        }
        });
    }

    @FXML
    public void historySelection(final MouseEvent event) {
        System.out.println("clicked on " + history.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void sendRequest(final ActionEvent event) {
        final var request = new Request();
        request.method = method.getValue();
        request.URL = urlField.getText();
        request.body = requestBody.getText();
        requestHeaders.forEach((key, value) -> request.headers.put(key, value));
        final var response = request.send();

        responseHeaders.clear();
        response.headers.forEach((key, values) -> responseHeaders.put(key, values.toString()));
        responseHeadersTable.getItems().setAll(responseHeaders.keySet());
        responseField.setText(response.body);
        history.getItems().add(new History(request));
    }

    @FXML
    public void close(final ActionEvent event){
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
