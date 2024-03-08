package com.gridnine.testing.service.impl;

import com.gridnine.testing.entity.Flight;
import com.gridnine.testing.entity.Segment;
import com.gridnine.testing.service.FlightFilterService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightFilterServiceImpl implements FlightFilterService {

    private final List<Flight> flights;

    public FlightFilterServiceImpl(List<Flight> flights) {
        this.flights = new ArrayList<>(flights);
    }


    @Override
    public List<Flight> removeDepartureBeforeThisTime(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .anyMatch(segment -> segment.getDepartureDate().isAfter(LocalDateTime.now())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> removeSegmentsDateArrivalBeforeDateDeparture(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .anyMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> removeFlightsMoreThanTwoHourOnTheGround(List<Flight> flights) {
        List<Flight> listLessTwoHour = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getSegments().size() == 1) {
                listLessTwoHour.add(flight);
            }
            List<Segment> segments = flight.getSegments();
            for (int i = 0; i < segments.size() - 1; i++) {
                LocalDateTime departureTime = segments.get(i + 1).getDepartureDate();
                LocalDateTime arrivalTime = segments.get(i).getArrivalDate();
                if (departureTime.isBefore(arrivalTime.plusHours(2))) {
                    listLessTwoHour.add(flight);
                }
            }
        }
        return listLessTwoHour;
    }
}
