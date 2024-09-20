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
    private FileIO<Events> fileIO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        initializeFieldsAndControls();
        prepareListsAndTableContent();
        setTableRowOnClickBehaviour();
    }

    private void initializeFieldsAndControls()
    {
        eventTable.getColumns().addAll(new TableColumns().getColumns());
        setComboBoxes();
        filters = new EventFilters(actionEvent -> filter());
        FilterPane.getChildren().addAll(filters.getFilterPanes());
        fileIO = new FileIOXML<>();
    }

    private void setComboBoxes()
    {
        typeComboBox.getItems().addAll(List.of(Event.EventType.values()));
        priorityComboBox.getItems().addAll(List.of(Event.EventPriority.values()));
    }

    private void prepareListsAndTableContent()
    {
        eventsObservableList = FXCollections.observableList(readEventsFromDefaultFile().getEventList());
        eventsFilteredList = FXCollections.observableList(new ArrayList<>());
        eventsFilteredList.addAll(eventsObservableList);
        SortedList<Event> sortedList = new SortedList<>(eventsFilteredList);
        eventTable.setItems(sortedList);
        sortedList.comparatorProperty().bind(eventTable.comparatorProperty());
    }

    private void setTableRowOnClickBehaviour()
    {
        eventTable.setRowFactory(eventTableView -> {
            TableRow<Event> row = new TableRow<>();
            row.setOnMouseClicked(ev->{
                if(ev.getClickCount() == 2 && !row.isEmpty())
                {
                    Event event = row.getItem();
                    Alert descriptionAlert = new Alert(Alert.AlertType.INFORMATION);
                    descriptionAlert.setHeaderText(event.getName());
                    descriptionAlert.setContentText(event.getDescription());
                    descriptionAlert.show();
                }
            });
            return row;
        });
    }

    private void error(String message)
    {
        Alert error = new Alert(Alert.AlertType.ERROR,message);
        error.show();
    }

    private void filter()
    {
        eventsFilteredList.clear();
        eventsFilteredList.addAll(eventsObservableList.filtered(
                event-> (filters.getCheckedTypes().isEmpty() || filters.getCheckedTypes().contains(event.getType()))
                    && (filters.getCheckedPriorities().isEmpty() || filters.getCheckedPriorities().contains(event.getPriority()))
                    && (event.getDate().isAfter(filters.getDateRange().dateFrom) && event.getDate().isBefore(filters.getDateRange().dateTo))
        ));
    }

    private Events readEventsFromDefaultFile(){
        Events events = new Events();
        try {
            events = fileIO.readFromFile(new File("default.xml"));
        }catch(Exception e) {
            error("Nie udało się otworzyć pliku domyślnego");
        }
        return events;
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

        return new Event(name,description,date,type,priority);
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
            Events events = fileIO.readFromFile();
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

    public void exportOnExit()
    {
        try {
            fileIO.writeToFile(new Events(eventsObservableList), new File("default.xml"));
        } catch (Exception e) {
            error("Nie można zapisać do pliku tymczasowego");
        }
    }
}