package com.example.eventmanager.Controls;

import com.example.eventmanager.model.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import java.util.List;
import java.util.stream.Collectors;

public class EventFilters
{
    FlowPane typeFilters;
    FlowPane priorityFilters;
    Label typeLabel;
    Label priorityLabel;
    EventHandler<ActionEvent> checkboxEvent;

    public EventFilters(EventHandler<ActionEvent> checkboxEvent)
    {
        this.checkboxEvent = checkboxEvent;
        typeFilters = new FlowPane();
        priorityFilters = new FlowPane();

        addTypeFilters();
        addPriorityFilters();

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

    public List<Node> getFilterPanes()
    {
        return List.of(typeLabel, typeFilters, priorityLabel, priorityFilters);
    }

    public List<Event.EventType> getCheckedTypes()
    {
        List<Event.EventType> types = typeFilters.getChildren().stream()
                .map((n)->(CheckBox)n)
                .filter(CheckBox::isSelected)
                .map(ch->Event.EventType.valueOf(ch.getText()))
                .collect(Collectors.toList());
        return types;
    }

    public List<Event.EventPriority> getCheckedPriorities()
    {
        List<Event.EventPriority> priorities = priorityFilters.getChildren().stream()
                .map((n)->(CheckBox)n)
                .filter(CheckBox::isSelected)
                .map(ch->Event.EventPriority.valueOf(ch.getText()))
                .collect(Collectors.toList());
        return priorities;
    }
}
