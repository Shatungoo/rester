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
    public TableView<String> requestHeaders;
    @FXML
    public TableColumn<String, String> name;
    @FXML
    public TableColumn<String, String> value;

    final HashMap<String, String> headers = new HashMap<>();

    @FXML
    public void initialize() {
        // Deafult method
        method.getItems().addAll("GET", "POST", "PUT");
        method.getSelectionModel().select("GET");

        // headers
        headers.put("key", "value");
        requestHeaders.getItems().addAll(headers.keySet());

        name.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()));
        value.setCellValueFactory(cd -> new SimpleStringProperty(headers.get(cd.getValue())));

        requestHeaders.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        value.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @FXML
    public void sendRequest(final ActionEvent event) {
        final HttpClient client = HttpClient.newHttpClient();
        final var requestBuilder = HttpRequest.newBuilder()
            .method(method.getValue(), BodyPublishers.ofString(requestBody.getText()))
            .uri(URI.create(urlField.getText()));
        headers.forEach((key, value) -> requestBuilder.header(key, value));
        client.sendAsync(requestBuilder.build(), 
            BodyHandlers
                .ofString())
                .thenApply(HttpResponse::body)
                .thenAccept((body) -> responseField.setText(body))
                .join();
        // try {
        // final var response = client.send(request, BodyHandlers.ofString());
        // responseField.setText(response.body());
        // } catch (IOException| InterruptedException e) {
        // e.printStackTrace();
        // }
    }

}
