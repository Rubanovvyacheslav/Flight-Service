package com.gridnine.testing;

import com.gridnine.testing.dao.FlightBuilder;
import com.gridnine.testing.entity.Flight;
import com.gridnine.testing.service.impl.FlightFilterServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        System.out.println("Unfiltered flights:\n" + flights);


        List<Flight> flightsWithoutDepartureBeforeThisTime = new FlightFilterServiceImpl(flights).removeDepartureBeforeThisTime(flights);
        System.out.println("Departure after this time:\n" + flightsWithoutDepartureBeforeThisTime);

        List<Flight> flightsWithoutSegmentsDateArrivalBeforeDateDeparture = new FlightFilterServiceImpl(flights).removeSegmentsDateArrivalBeforeDateDeparture(flights);
        System.out.println("Arrival after Departure:\n" + flightsWithoutSegmentsDateArrivalBeforeDateDeparture);

        List<Flight> flightsWithoutMoreThanTwoHourOnTheGround = new FlightFilterServiceImpl(flights).removeFlightsMoreThanTwoHourOnTheGround(flights);
        System.out.println("Less two hour between Arrival and Departure:\n" + flightsWithoutMoreThanTwoHourOnTheGround);
    }
}