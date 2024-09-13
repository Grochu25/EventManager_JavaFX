package com.example.eventmanager.Controls;

import com.example.eventmanager.model.Event;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EventTableView extends TableView<Event>
{
    public EventTableView()
    {
        setColumnsInTable();
    }

    private void setColumnsInTable()
    {
        setColumn("Nazwa","name");
        setColumn("Data","datetime");
        setColumn("Typ","type");
        setColumn("Priorytet","priority");
    }

    private void setColumn(String columnName, String propertyName)
    {
        TableColumn<Event, String> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<Event,String>(propertyName));
        this.getColumns().add(column);
    }
}
