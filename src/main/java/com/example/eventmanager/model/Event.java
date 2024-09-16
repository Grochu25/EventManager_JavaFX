package com.example.eventmanager.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Event implements Comparable, Serializable
{
    //TODO: Klasy i możliwość porównania
    public enum EventType {BRAK, PRACA, RODZINA, ROZRYWKA, ZDROWIE, SPORT}
    public enum EventPriority {BRAK, WYSOKI, SREDNI, NISKI}

    private String name;
    private String description;
    private LocalDateTime datetime;
    private EventType type;
    private EventPriority priority;

    public Event(String name, String description, LocalDateTime datetime, EventType type, EventPriority priority)
    {
        this.name = name;
        this.description = description;
        this.datetime = datetime;
        this.type = type;
        this.priority = priority;
    }

    public Event(){
        this("","",LocalDateTime.now(),EventType.BRAK,EventPriority.BRAK);
    }

    @Override
    public int compareTo(Object o)
    {
        if(o == null) return -1;
        if(o == this) return 0;
        if(!(o instanceof Event)) return 1;
        Event casted = (Event) o;
        return this.name.compareTo(casted.name);
    }

    @Override
    public String toString()
    {
        return name+" "+description+" "+datetime+" "+type+" "+priority;
    }

    public String getName(){return name;}
    public String getDescription(){return description;}
    public String getDatetime(){return datetime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT,FormatStyle.SHORT));}
    public EventType getType(){return type;}
    public EventPriority getPriority(){return priority;}

    public void setName(String name){this.name = name;}
    public void setDescription(String description){this.description = description;}
    public void setDatetime(String datetime){this.datetime = LocalDateTime.parse(datetime,DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT,FormatStyle.SHORT));}
    public void setType(EventType type){this.type = type;}
    public void setPriority(EventPriority priority){this.priority = priority;}
}
