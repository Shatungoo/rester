package com.helldaisy;

import java.util.HashMap;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

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
    public void initialize() {
        // Deafult method
        method.getItems().addAll("GET", "POST", "PUT", "DELETE","PATCH");
        method.getSelectionModel().select("GET");

        // headers
        requestHeaders.put("key", "value");

        setTableOptions(requestHeadersTable, requestHeaders);
        setTableOptions(responseHeadersTable, responseHeaders);   
    }

    void setTableOptions(TableView<String> tv, HashMap<String, String> map) {
        var name = new TableColumn<String, String>("Name");
        var value = new TableColumn<String, String>("Value");
        tv.getColumns().addAll(name, value);

        tv.getItems().addAll(map.keySet());

        name.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()));
        value.setCellValueFactory(cd -> new SimpleStringProperty(map.get(cd.getValue())));

        tv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        value.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @FXML
    public void sendRequest(final ActionEvent event) {
        var request = new Request();
        request.method = method.getValue();
        request.URL = urlField.getText();
        request.body = requestBody.getText();
        requestHeaders.forEach((key, value) -> request.headers.put(key, value));
        var response = request.send();

        responseHeaders.clear();
        response.headers.forEach((key, values) -> responseHeaders.put(key, values.toString()));
        responseHeadersTable.getItems().setAll(responseHeaders.keySet());
        responseField.setText(response.body);
    }
}
