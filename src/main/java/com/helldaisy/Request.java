package com.helldaisy;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    public TableView requestHeaders;
    
    @FXML
public void initialize() {
    method.getItems().addAll("GET", "POST", "PUT");
    method.getSelectionModel().select("GET");
}

    @FXML
    public void sendRequest(final ActionEvent event) {
        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder()
            // .headers(headers)
            .method(method.getValue(),
                BodyPublishers.ofString(requestBody.getText()))
            .uri(URI.create(urlField.getText())).build();
        client.sendAsync(request, BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept((body)-> { responseField.setText(body);})
            .join();
        // try {
        //     final var response = client.send(request, BodyHandlers.ofString());
        //     responseField.setText(response.body());
        // } catch (IOException| InterruptedException e) {
        //     e.printStackTrace();
        // }
    }

}
