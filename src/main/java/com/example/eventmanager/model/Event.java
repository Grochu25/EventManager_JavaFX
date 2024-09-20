package com.example.eventmanager.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class Event implements Comparable<Event>, Serializable
{
    public enum EventType {BRAK, PRACA, RODZINA, ROZRYWKA, ZDROWIE, SPORT}
    public enum EventPriority {BRAK, WYSOKI, SREDNI, NISKI}

    private String name;
    private String description;
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate date;
    private EventType type;
    private EventPriority priority;

    public Event(String name, String description, LocalDate datetime, EventType type, EventPriority priority)
    {
        this.name = name;
        this.description = description;
        this.date = datetime;
        this.type = type;
        this.priority = priority;
    }

    public Event(){
        this("","",LocalDate.now(),EventType.BRAK,EventPriority.BRAK);
    }

    @Override
    public int compareTo(Event other)
    {
        if(other == null) return -1;
        if(other == this) return 0;
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString()
    {
        return name+" "+description+" "+ date +" "+type+" "+priority;
    }

    public String getName(){return name;}
    public String getDescription(){return description;}
    public LocalDate getDate(){return date;}
    public EventType getType(){return type;}
    public EventPriority getPriority(){return priority;}

    public void setName(String name){this.name = name;}
    public void setDescription(String description){this.description = description;}
    public void setDate(LocalDate date){this.date = date;}
    public void setType(EventType type){this.type = type;}
    public void setPriority(EventPriority priority){this.priority = priority;}
}
