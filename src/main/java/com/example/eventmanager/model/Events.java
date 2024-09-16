package com.example.eventmanager.model;

import jakarta.xml.bind.annotation.*;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Events
{
    @XmlElementWrapper(name="eventList")
    @XmlElement(name="event")
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
