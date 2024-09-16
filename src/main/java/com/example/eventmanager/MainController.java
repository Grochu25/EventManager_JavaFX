package com.example.eventmanager;

import com.example.eventmanager.Controls.EventFilters;
import com.example.eventmanager.Controls.TableColumns;
import com.example.eventmanager.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private ComboBox<Event.EventPriority> priorityComboBox;
    @FXML
    private ComboBox<Event.EventType> typeComboBox;
    @FXML
    private VBox FilterPane;

    private ObservableList<Event> eventsObservableList;
    private ObservableList<Event> eventsFilteredList;
    private EventFilters filters;
    private FileIO fileIO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        initializeFieldsAndControls();

        prepareListsAndTableContent();
    }

    private void initializeFieldsAndControls()
    {
        eventTable.getColumns().addAll(new TableColumns().getColumns());
        setComboBoxes();
        filters = new EventFilters(actionEvent -> {filter();});
        FilterPane.getChildren().addAll(filters.getFilterPanes());
        fileIO = new FileIOXML();
    }

    private void prepareListsAndTableContent()
    {
        SortedList<Event> sortedList = new SortedList<Event>(FXCollections.observableList(new ArrayList<Event>()));
        try {
            Events events = readEventsFromDefaultFile();
            eventsObservableList = FXCollections.observableList(events.getEventList());
            eventsFilteredList = FXCollections.observableList(new ArrayList<Event>());
            eventsFilteredList.addAll(eventsObservableList);
            sortedList = new SortedList<Event>(eventsFilteredList);
            eventTable.setItems(sortedList);
            sortedList.comparatorProperty().bind(eventTable.comparatorProperty());
        }catch(Exception e)
        {
            error("Nie udało się otworzyć pliku domyślnego");
        }
    }

    private void setComboBoxes()
    {
        typeComboBox.getItems().addAll(List.of(Event.EventType.values()));
        priorityComboBox.getItems().addAll(List.of(Event.EventPriority.values()));
    }

    private void error(String message)
    {
        Alert error = new Alert(Alert.AlertType.ERROR,message);
        error.show();
    }

    private void filter()
    {
        eventsFilteredList.clear();
        eventsFilteredList.addAll(eventsObservableList.filtered(event->{
            return (filters.getCheckedTypes().isEmpty() || filters.getCheckedTypes().contains(event.getType()))
                    && (filters.getCheckedPriorities().isEmpty() || filters.getCheckedPriorities().contains(event.getPriority()));
        }));
    }

    private Events readEventsFromDefaultFile() throws Exception {
        return (Events) fileIO.readFromFile(new File("default.xml"));
    }


    private void clearInputs()
    {
        nameInput.clear();
        descriptionInput.clear();
        dateInput.setValue(null);
        typeComboBox.setValue(null);
        priorityComboBox.setValue(null);
    }

    public void addNewEvent()
    {
        Event newEvent = getEventFromInputs();

        if(newEvent != null) {
            eventsObservableList.add(newEvent);
            clearInputs();
            filter();
        }
    }

    private Event getEventFromInputs()
    {
        String name = nameInput.getText();
        String description = descriptionInput.getText();
        LocalDate date = dateInput.getValue();
        Event.EventType type = typeComboBox.getValue();
        Event.EventPriority priority = priorityComboBox.getValue();

        if(name.isEmpty() || description.isEmpty() || date == null || type == null || priority == null) {
            error("Wypełnij wszystkie pola przed dodaniem wydarzenia");
            return null;
        }

        return new Event(name,description,date.atStartOfDay(),type,priority);
    }

    public void deleteChoosedEvent()
    {
        Event selected = eventTable.getSelectionModel().getSelectedItem();
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Uwaga");
        deleteAlert.setHeaderText("Czy na pewno usunąć "+selected.getName()+"?");

        if(deleteAlert.showAndWait().get() == ButtonType.OK)
            eventsObservableList.remove(selected);
        filter();
    }

    public void importEvents()
    {
        try {
            Events events = (Events) fileIO.readFromFile();
            eventsObservableList.clear();
            eventsObservableList.addAll(events.getEventList());
        } catch (Exception e) {
            error("Nie można otworzyć pliku lub jego zawartości");
        }
        filter();
    }

    public void exportEvents() {
        try {
            fileIO.writeToFile(new Events(eventsObservableList));
        } catch (Exception e) {
            error("Nie można zapisać pliku");
        }
    }
}