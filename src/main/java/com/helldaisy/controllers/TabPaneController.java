package com.helldaisy.controllers;

import java.util.ArrayList;

import com.helldaisy.model.*;
import com.helldaisy.uiModel.*;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

public class TabPaneController {
    @FXML
    public ListView<Exchange> historyView;

    @FXML
    public TreeView<CollectionFX> collections;

    public Main main;

    public Exchange history;

    @FXML
    public void initialize() {
        historyView.setCellFactory(history -> new ListCell<Exchange>() {
            @Override
            protected void updateItem(final Exchange history, final boolean empty) {
                super.updateItem(history, empty);
                if (history != null && history.request!=null) {
                    final var request = history.request;
                    setText(request.method + " " + request.URL);
                    return;
                } 
                setText("");
            }
        });

        var root = new TreeItem<CollectionFX>(new CollectionFX(new Collection("RootCollection")));
        collections.setShowRoot(false);
        collections.setRoot(root);
    }

    
    @FXML
    public void historySelection(final MouseEvent event) {
        var request = historyView.getSelectionModel().getSelectedItem().request;
        var response = historyView.getSelectionModel().getSelectedItem().response;
        main.setRequest (request);
        main.setResponse(response);
    }

    
    @FXML
    public void selectRequest(final MouseEvent event) {
        var selected = collections.getSelectionModel().getSelectedItem().getValue();
        if (selected.collection != null) return;
        main.setRequest (selected.request);
    }
    

    public void addHistory(Exchange history){
        if (history == null ) return;
        historyView.getItems().add(history);
    }

    public void addHistory(ArrayList<Exchange> history){
        history.removeIf(filter -> filter == null);
        historyView.getItems().addAll(history);
    }

    public void addRequestToCollection(Request request){
        var col1 = new TreeItem<CollectionFX>(new CollectionFX(request));
        collections.getRoot().getChildren().add(col1);
    }
    

}