package com.helldaisy.controllers;

import java.util.HashMap;

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

public class ResponseController {
    @FXML
    public TableView<String> responseHeadersTable;

    final HashMap<String, String> responseHeaders = new HashMap<>();
    
    @FXML
    public TextArea responseField;

    @FXML
    public TableColumn<String, String> responseName, responseValue;


    @FXML
    public void initialize() {

        responseHeadersTable.getItems().addAll(responseHeaders.keySet());
        responseName.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()));
        responseValue.setCellValueFactory(cd -> new SimpleStringProperty(responseHeaders.get(cd.getValue())));
        responseName.setCellFactory(TextFieldTableCell.forTableColumn());
        responseValue.setCellFactory(TextFieldTableCell.forTableColumn());
    
       
    }
    public void setResponse(Response response) {
        responseHeaders.clear();
        response.headers.forEach((key, values) -> responseHeaders.put(key, values.toString()));
        responseHeadersTable.getItems().setAll(responseHeaders.keySet());
        responseField.setText(response.body);
    }

}
