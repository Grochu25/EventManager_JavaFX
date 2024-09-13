module com.example.eventmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.xml.bind;


    opens com.example.eventmanager to javafx.fxml, jakarta.xml.bind;
    exports com.example.eventmanager;
    opens com.example.eventmanager.model to javafx.fxml, jakarta.xml.bind;
    exports com.example.eventmanager.model;
}