package com.helldaisy.controllers;

import java.util.ArrayList;
import java.util.Optional;

// import io.swagger.OpenAPIV3Parser;
// import io.swagger.v3.oas.models.OpenAPI;
import com.helldaisy.Collection;
import com.helldaisy.CollectionFX;
import com.helldaisy.History;
import com.helldaisy.Request;
import com.helldaisy.Response;
import com.helldaisy.Utils.Loader;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
public class Main {
    

    @FXML
    public ListView<History> history;

    @FXML
    public TitleController titleControllerController;
    
    @FXML
    public ResponseController responseControllerController;

    @FXML
    public RequestController requestControllerController;

    @FXML
    public TreeView<CollectionFX> collections;


    @FXML
    public void initialize() {
        var settings = Optional
            .ofNullable(Loader.load())
            .orElse(new ArrayList<History> ());

        titleControllerController.main = this;
        history.getItems().addAll(settings);

        requestControllerController.main = this;

        var root = new TreeItem<CollectionFX>(new CollectionFX(new Collection("RootCollection")));
        var col1 = new TreeItem<CollectionFX>(new CollectionFX(new Collection("Collection1")));
        root.getChildren().add(col1);
        var req = new Request();
        req.URL = "htttp//bash.org";
        col1.getChildren().add(new TreeItem<CollectionFX>(new CollectionFX(req)));

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

        // germanics.getChildren().add(new TreeItem<String>("German"));
        collections.setShowRoot(false);

        collections.setRoot(root);
        // OpenAPI oa = new OpenAPIV3Parser().read("");
        

    }

    @FXML
    public void historySelection(final MouseEvent event) {
        var request = history.getSelectionModel().getSelectedItem().request;
        var response = history.getSelectionModel().getSelectedItem().response;
        requestControllerController.setRequest (request);
        setResponse(response);
    }

    public void setResponse(Response response) {
        this.responseControllerController.setResponse(response);
    }

    public void close(){
        Loader.save(new ArrayList(history.getItems()));
    }


}
