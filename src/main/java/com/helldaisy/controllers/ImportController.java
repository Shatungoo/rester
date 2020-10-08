package com.helldaisy.controllers;

import com.helldaisy.*;
import com.helldaisy.model.Request;

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

public class ImportController {

    @FXML
    public TextArea rawText;
    
    public Main main;

    @FXML
    public void initialize() {
        
    }

    @FXML
    public void importFromCurl(final ActionEvent event){
        var curl = rawText.getText();
        Request request = Request.importFromCurl(curl);
        main.setRequest(request);
    }

    @FXML
    public void importFromSwagger(final ActionEvent event){
        var curl = rawText.getText();
        Request request = Request.importFromCurl(curl);
        main.setRequest(request);
    }

}
