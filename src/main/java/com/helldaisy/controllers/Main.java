package com.helldaisy.controllers;

import java.util.ArrayList;
import java.util.Optional;

import com.helldaisy.Exchange;
import com.helldaisy.Request;
import com.helldaisy.Response;
import com.helldaisy.Utils.Loader;

import javafx.fxml.FXML;
public class Main {
    
    @FXML
    public TitleController titleControllerController;
    
    @FXML
    public ResponseController responseControllerController;

    @FXML
    public RequestController requestControllerController;

    @FXML
    public TabPaneController tabPaneControllerController;



    @FXML
    public void initialize() {
        var settings = Optional
            .ofNullable(Loader.load())
            .orElse(new ArrayList<Exchange> ());

        titleControllerController.main = this;
        tabPaneControllerController.main = this;
        tabPaneControllerController.addHistory(settings);
        tabPaneControllerController.main = this;
        requestControllerController.main = this;
    }


    public void setResponse(Response response) {
        this.responseControllerController.setResponse(response);
    }

    public void setRequest(Request request) {
        this.requestControllerController.setRequest(request);
    }

    public void close(){
        Loader.save(new ArrayList(tabPaneControllerController.historyView.getItems()));
    }


	public void addHistory(Exchange history) {
        tabPaneControllerController.addHistory(history);
	}


}
