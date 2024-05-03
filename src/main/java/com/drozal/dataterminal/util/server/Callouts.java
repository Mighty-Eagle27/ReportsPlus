package com.drozal.dataterminal.util.server;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "Callouts")
@XmlAccessorType(XmlAccessType.FIELD)
public class Callouts {

    @XmlElement(name = "Callout")
    private List<Callout> calloutList;

    // Getter and setter for the list of Callouts
    public List<Callout> getCalloutList() {
        return calloutList;
    }

    public void setCalloutList(List<Callout> calloutList) {
        this.calloutList = calloutList;
    }
}
