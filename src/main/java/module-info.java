module com.helldaisy {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.net.http;
    requires org.jfxtras.styles.jmetro;
    requires swagger.parser;
    requires com.google.gson;
    exports com.helldaisy.model to com.google.gson;
    exports com.helldaisy;
    exports com.helldaisy.controllers;
    
}