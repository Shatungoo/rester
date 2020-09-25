package com.helldaisy.controllers;

import com.helldaisy.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;

public class RequestController {

    @FXML
    public TextField urlField;

    @FXML
    public ChoiceBox<String> method;

    @FXML
    public TextArea requestBody;

    @FXML
    public TableColumn<Pair, String> requestName, requestValue;

    @FXML
    public TableView<Pair> requestHeadersTable;
    
    ObservableList<Pair> requestHeaders = FXCollections
        .observableArrayList(new Pair("", ""));

    @FXML
    public TableColumn<String, String> responseName, responseValue;

    class Pair{
        String key, value;
        public Pair (String key, String value){
            this.key= key;
            this.value = value;
        }
    }

    public Main main;

    @FXML
    public void initialize() {
        method.getItems().addAll("GET", "POST", "PUT", "DELETE", "PATCH");
        method.getSelectionModel().select("GET");
        requestHeadersTable.getItems().addAll(requestHeaders);
        requestName.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().key));
        requestName.setCellFactory(TextFieldTableCell.forTableColumn());
        requestValue.setCellFactory(TextFieldTableCell.forTableColumn());
        requestValue.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().value));
        requestHeadersTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        requestName.setOnEditCommit((CellEditEvent<Pair, String> event) -> {
            var pos = event.getTablePosition();
            var oldValue = event.getOldValue();
            int row = pos.getRow();
            var pair = event.getTableView().getItems().get(row);
            pair.key = event.getNewValue();
            if (oldValue.isEmpty())
                event.getTableView().getItems().add(new Pair("", ""));
        });

        requestValue.setOnEditCommit((CellEditEvent<Pair, String> event) -> {
            var pos = event.getTablePosition();
            String newFullName = event.getNewValue();
            int row = pos.getRow();
            var pair = event.getTableView().getItems().get(row);
            pair.value = newFullName;
        });

        requestHeadersTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public Request getRequest() {
        final Request request = new Request();
        request.method = method.getValue();
        request.URL = urlField.getText();
        request.body = requestBody.getText();
        requestHeaders.forEach(key -> request.headers.put(key.key, key.value));
        return request;
    }

    public void setRequest(final Request request) {
        method.setValue(request.method);
        urlField.setText(request.URL);
        requestBody.setText(request.body);
    }

    @FXML
    public void sendRequest(final ActionEvent event) {
        final var request = getRequest();
        final var response = request.send();
        main.setResponse(response);
        main.addHistory(new Exchange(request, response));
    }
}
