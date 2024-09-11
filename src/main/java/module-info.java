module com.example.eventmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.dataformat.xml;
    requires com.fasterxml.jackson.core;


    opens com.example.eventmanager to javafx.fxml;
    exports com.example.eventmanager;
    opens com.example.eventmanager.model to javafx.fxml;
    exports com.example.eventmanager.model;
}