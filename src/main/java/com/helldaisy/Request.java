package com.helldaisy;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

public class Request {

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
        final HttpClient client = HttpClient.newHttpClient();
        var url = urlField.getText();
        var uri= (!url.toLowerCase().matches("^\\w+://.*"))
            ? "http://" + url
            :url;
        final var requestBuilder = HttpRequest.newBuilder()
                .method(method.getValue(), BodyPublishers.ofString(requestBody.getText()))
                .uri(URI.create(uri));
        requestHeaders.forEach((key, value) -> requestBuilder.header(key, value));
        client.sendAsync(requestBuilder.build(), 
            BodyHandlers.ofString())
                .thenApply(response -> { 
                    responseHeaders.clear();
                    response.headers().map().forEach((key, values) -> 
                        responseHeaders.put(key, values.toString()));
                    responseHeadersTable.getItems().setAll(responseHeaders.keySet());
                    responseField.setText(response.body());
                    return response; 
                } )
                .join();
    }

}
