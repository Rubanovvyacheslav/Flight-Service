package com.gridnine.testing.service;

import com.gridnine.testing.entity.Flight;

import java.util.List;

public interface FlightFilterService {


    List<Flight> removeDepartureBeforeThisTime(List<Flight> flights);
    List<Flight> removeSegmentsDateArrivalBeforeDateDeparture(List<Flight> flights);

    List<Flight> removeFlightsMoreThanTwoHourOnTheGround(List<Flight> flights);
}
