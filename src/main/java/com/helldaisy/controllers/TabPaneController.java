package com.helldaisy.controllers;

import java.util.ArrayList;

import com.helldaisy.Collection;
import com.helldaisy.CollectionFX;
import com.helldaisy.History;
import com.helldaisy.Request;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

public class TabPaneController {
    @FXML
    public ListView<History> historyView;

    @FXML
    public TreeView<CollectionFX> collections;

    public Main main;

    public History history;

    @FXML
    public void initialize() {
        historyView.setCellFactory(history -> new ListCell<History>() {
            @Override
            protected void updateItem(final History history, final boolean empty) {
                super.updateItem(history, empty);
                if (history != null && history.request!=null) {
                    final var request = history.request;
                    setText(request.method + " " + request.URL);
                }
            }
        });

        var root = new TreeItem<CollectionFX>(new CollectionFX(new Collection("RootCollection")));
        var col1 = new TreeItem<CollectionFX>(new CollectionFX(new Collection("Collection1")));
        root.getChildren().add(col1);
        var req = new Request();
        req.URL = "htttp//bash.org";
        col1.getChildren().add(new TreeItem<CollectionFX>(new CollectionFX(req)));
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
    

    public void addHistory(History history){
        if (history == null ) return;
        historyView.getItems().add(history);
    }

    public void addHistory(ArrayList<History> history){
        history.removeIf(filter -> filter == null);
        historyView.getItems().addAll(history);
    }
    

}
