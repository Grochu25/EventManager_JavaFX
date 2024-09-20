package com.example.eventmanager.Controls;

import com.example.eventmanager.model.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EventFilters
{
    FlowPane typeFilters;
    FlowPane priorityFilters;
    VBox dateFilters;
    Label typeLabel;
    Label priorityLabel;
    EventHandler<ActionEvent> checkboxEvent;

    CheckBox dateUse;
    DatePicker dateFromPicker;
    DatePicker dateToPicker;

    public EventFilters(EventHandler<ActionEvent> checkboxEvent)
    {
        this.checkboxEvent = checkboxEvent;

        initializeFields();

        addTypeFilters();
        addPriorityFilters();
        addDateFilters();
    }

    private void initializeFields()
    {
        typeFilters = new FlowPane();
        priorityFilters = new FlowPane();
        dateFilters = new VBox();
        dateFromPicker = new DatePicker();
        dateToPicker = new DatePicker();
        dateUse = new CheckBox("dacie: ");
        typeLabel = new Label("typie: ");
        priorityLabel = new Label("priorytecie: ");
    }

    private void addTypeFilters()
    {
        Event.EventType[] eventValues = Event.EventType.values();
        for(Event.EventType type : eventValues)
        {
            CheckBox checkBox = new CheckBox(type.toString());
            checkBox.setPadding(new Insets(0,5,5,0));
            checkBox.setOnAction(checkboxEvent);
            typeFilters.getChildren().add(checkBox);
        }
    }

    private void addPriorityFilters()
    {
        Event.EventPriority[] eventValues = Event.EventPriority.values();
        for(Event.EventPriority priority : eventValues)
        {
            CheckBox checkBox = new CheckBox(priority.toString());
            checkBox.setPadding(new Insets(0,5,5,0));
            checkBox.setOnAction(checkboxEvent);
            priorityFilters.getChildren().add(checkBox);
        }
    }

    private void addDateFilters()
    {
        setStyleToDateElements();
        setActionsToDateElements();
        addAllTo(dateFilters, dateUse, new Label("Od:"), dateFromPicker,new Label("Do:"), dateToPicker);
    }

    private void setStyleToDateElements()
    {
        dateUse.setPadding(new Insets(0, 10, 0, 0));
        dateFromPicker.setPadding(new Insets(0, 5, 0, 0));
        dateToPicker.setPadding(new Insets(0, 5, 0, 0));
        dateFromPicker.setDisable(true);
        dateToPicker.setDisable(true);
    }

    private void setActionsToDateElements()
    {
        dateUse.setOnMouseClicked(e->enableDataFilters());
        dateUse.setOnAction(checkboxEvent);
        dateToPicker.setOnAction(checkboxEvent);
        dateFromPicker.setOnAction(checkboxEvent);
    }

    private void addAllTo(Pane pane, Control... controls)
    {
        for(Control control : controls)
            pane.getChildren().add(control);
    }

    private void enableDataFilters()
    {
         dateToPicker.setDisable(!dateUse.isSelected());
         dateFromPicker.setDisable(!dateUse.isSelected());
    }

    public List<Node> getFilterPanes()
    {
        return List.of(typeLabel, typeFilters, priorityLabel, priorityFilters, dateFilters);
    }

    public List<Event.EventType> getCheckedTypes()
    {
        return typeFilters.getChildren().stream()
                .map((n)->(CheckBox)n)
                .filter(CheckBox::isSelected)
                .map(ch->Event.EventType.valueOf(ch.getText()))
                .collect(Collectors.toList());
    }

    public List<Event.EventPriority> getCheckedPriorities()
    {
        return priorityFilters.getChildren().stream()
                .map((n)->(CheckBox)n)
                .filter(CheckBox::isSelected)
                .map(ch->Event.EventPriority.valueOf(ch.getText()))
                .collect(Collectors.toList());
    }

    public DateRange getDateRange()
    {
        if(dateUse.isSelected())
        {
            LocalDate dateForm = (dateFromPicker.getValue()!=null)? dateFromPicker.getValue() : LocalDate.MIN;
            LocalDate dateTo = (dateToPicker.getValue()!=null)? dateToPicker.getValue() : LocalDate.MAX;
            return new DateRange(dateForm,dateTo);
        }
        else
            return new DateRange(LocalDate.MIN,LocalDate.MAX);
    }
}
