package com.example.eventmanager;

import com.example.eventmanager.Controls.EventTableView;
import com.example.eventmanager.model.Event;
import com.example.eventmanager.model.Events;
import jakarta.xml.bind.*;
import javafx.collections.FXCollections;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
        eventTable.getColumns().addAll(new EventTableView().getColumns());

        SortedList<Event> sortedList = new SortedList<Event>(FXCollections.emptyObservableList());
        try {
            Events events = readEventsFromDefaultFile();
            sortedList = new SortedList<Event>(FXCollections.observableList(events.getEventList()));
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        eventTable.setItems(sortedList);
        sortedList.comparatorProperty().bind(eventTable.comparatorProperty());
    }

    private Events readEventsFromDefaultFile() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(Events.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        Events x = new Events(List.of(new Event("TUBA","nie",LocalDateTime.now(), Event.EventType.BRAK, Event.EventPriority.BRAK)));
//        marshaller.marshal(x, System.out);

        File file = new File("default.xml");
        Events events = (Events) unmarshaller.unmarshal(file);
        return events;
    }
}