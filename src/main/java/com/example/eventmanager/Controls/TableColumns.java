package com.example.eventmanager.Controls;

import com.example.eventmanager.model.Event;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class TableColumns
{
    private final List<TableColumn<Event, String>> columns = new ArrayList<>();

    public TableColumns()
    {
        setColumnsInTable();
    }

    private void setColumnsInTable()
    {
        setColumn("Nazwa","name");
        setColumn("Data","date");
        setColumn("Typ","type");
        setColumn("Priorytet","priority");
    }

    private void setColumn(String columnName, String propertyName)
    {
        TableColumn<Event, String> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        columns.add(column);
    }

    public List<TableColumn<Event, String>> getColumns(){return columns;}
}
