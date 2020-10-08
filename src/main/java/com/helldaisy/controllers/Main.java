package com.helldaisy.controllers;

import java.util.ArrayList;
import java.util.Optional;

import com.helldaisy.model.*;
import com.helldaisy.Utils.Loader;
import javafx.fxml.FXML;
public class Main {
    
    @FXML
    public TitleController titleController;
    
    @FXML
    public ResponseController responseController;

    @FXML
    public RequestController requestController;

    @FXML
    public TabPaneController tabPaneController;


    @FXML
    public void initialize() {
        var settings = Optional
            .ofNullable(Loader.load())
            .orElse(new ArrayList<Exchange> ());

        titleController.main = this;
        tabPaneController.main = this;
        tabPaneController.addHistory(settings);
        tabPaneController.main = this;
        requestController.main = this;
    }


    public void setResponse(Response response) {
        this.responseController.setResponse(response);
    }

    public void setRequest(Request request) {
        this.requestController.setRequest(request);
    }

    public void close(){
        Loader.save(new ArrayList(tabPaneController.historyView.getItems()));
    }


	public void addHistory(Exchange history) {
        tabPaneController.addHistory(history);
	}


	public void saveRequest(Request request) {
        tabPaneController.addRequestToCollection(request);
	}


}
