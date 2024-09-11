package com.example.eventmanager;

import com.example.eventmanager.model.Event;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable
{
    @FXML
    private TableView<Event> eventTable;
    @FXML
    private TextField nameInput;
    @FXML
    private TextArea descriptionInput;
    @FXML
    private DatePicker dateInput;
    @FXML
    private ComboBox<String> priorityComboBox;
    @FXML
    private ComboBox<String> typeComboBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        setColumnsInTable();
        ObservableList<Event> lista = FXCollections.observableList(List.of(
                new Event("tuba","brak",LocalDateTime.now(), Event.EventType.PRACA, Event.EventPriority.WYSOKI),
                new Event("dupa","brak",LocalDateTime.now(), Event.EventType.BRAK, Event.EventPriority.NISKI)));
        SortedList sl = new SortedList<>(lista);
        eventTable.setItems(sl);
        sl.comparatorProperty().bind(eventTable.comparatorProperty());

        //Event[] events = readFromDefaultFile();
        try {
            readFromDefaultFile();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setColumnsInTable()
    {
        setColumn("Nazwa","name");
        setColumn("Data","datetime");
        setColumn("Typ","type");
        setColumn("Priorytet","priority");
        //setColumn("Opis","description");
    }

    private void setColumn(String columnName, String propertyName)
    {
        TableColumn<Event, String> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<Event,String>(propertyName));
        eventTable.getColumns().add(column);
    }

    private Event[] readFromDefaultFile() throws IOException {
        XmlMapper mapper = new XmlMapper();
        File file = new File("default.xml");
        SortedList<Event> events = mapper.readValue(file, SortedList.class);
        return null;
    }
}