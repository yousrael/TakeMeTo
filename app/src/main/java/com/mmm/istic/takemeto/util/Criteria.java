package com.mmm.istic.takemeto.util;



import java.util.Date;

/**
 * Created by steve on 20/03/17.
 */

public class Criteria {

    private String departure ;
    private String arrival;
    private String departureDate;

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }
}