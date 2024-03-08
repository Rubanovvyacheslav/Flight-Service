package com.gridnine.testing.service.impl;

import com.gridnine.testing.entity.Flight;
import com.gridnine.testing.entity.Segment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlightFilterServiceImplTest {
    private static final LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);

    //Нормальный перелет с двухчасовой пересадкой
    public static Flight normalFlight = new Flight(List.of(
            new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(2))
    ));

    //Нормальный перелет с 2 сегментами
    public static Flight twoSegmentFlight = new Flight(List.of(
            new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
            new Segment(threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5))
    ));

    //Перелет с вылетом в прошлом
    public static Flight pastDepartureFlight = new Flight(List.of(
            new Segment(threeDaysFromNow.minusDays(6), threeDaysFromNow)
    ));

    //Перелет с прибытием раньше вылета
    public static Flight depBeforeArrFlights = new Flight(List.of(
            new Segment(threeDaysFromNow, threeDaysFromNow.minusHours(6))
    ));

    //Перелет с пересадкой более 2 часов
    public static Flight moreTwoHourGround = new Flight(List.of(
            new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
            new Segment(threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6))
    ));

    //Перелет с пересадкой 2 часа
    public static Flight twoHourGround = new Flight(List.of(
            new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
            new Segment(threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4)),
            new Segment(threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7))
    ));

    //Создание списков actual

    public static List<Flight> allFlight = Arrays.asList(normalFlight, twoSegmentFlight, pastDepartureFlight, depBeforeArrFlights, moreTwoHourGround, twoHourGround);
    public static List<Flight> actualWithoutPastDeparture = Arrays.asList(normalFlight, twoSegmentFlight, depBeforeArrFlights, moreTwoHourGround, twoHourGround);
    public static List<Flight> actualWithoutDepBeforeArr = Arrays.asList(normalFlight, twoSegmentFlight, pastDepartureFlight, moreTwoHourGround, twoHourGround);
    public static List<Flight> actualWithoutMoreTwoHourGround = Arrays.asList(normalFlight, twoSegmentFlight, pastDepartureFlight, depBeforeArrFlights, twoHourGround);


    @Test
    void removeDepartureBeforeThisTime() {
        List<Flight> expected = new FlightFilterServiceImpl(allFlight).removeDepartureBeforeThisTime(allFlight);
        assertEquals(expected, actualWithoutPastDeparture);

    }

    @Test
    void removeSegmentsDateArrivalBeforeDateDeparture() {
        List<Flight> expected = new FlightFilterServiceImpl(allFlight).removeSegmentsDateArrivalBeforeDateDeparture(allFlight);
        assertEquals(expected, actualWithoutDepBeforeArr);
    }

    @Test
    void removeFlightsMoreThanTwoHourOnTheGround() {
        List<Flight> expected = new FlightFilterServiceImpl(allFlight).removeFlightsMoreThanTwoHourOnTheGround(allFlight);
        assertEquals(expected, actualWithoutMoreTwoHourGround);
    }
}

