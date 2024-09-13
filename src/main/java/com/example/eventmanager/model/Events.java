package com.example.eventmanager.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Events
{
    private List<Event> eventList;

    public Events(){
        eventList = new ArrayList<Event>();
    }

    public Events(Collection c)
    {
        eventList = new ArrayList<>(c);
    }

    public List<Event> getEventList(){return eventList;}
    public void setEventList(List<Event> newList) {eventList = newList;}
}
