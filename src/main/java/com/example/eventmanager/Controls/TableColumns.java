package com.example.eventmanager.Controls;

import com.example.eventmanager.model.Event;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

//TODO: To można zamienić na coś bardziej jak lista
public class TableColumns
{
    private List<TableColumn<Event, String>> columns = new ArrayList<>();

    public TableColumns()
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
        columns.add(column);
    }

    public List<TableColumn<Event, String>> getColumns(){return columns;}
}
