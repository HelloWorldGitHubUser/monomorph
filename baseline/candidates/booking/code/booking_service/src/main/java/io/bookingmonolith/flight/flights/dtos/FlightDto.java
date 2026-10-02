package io.bookingmonolith.flight.flights.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record FlightDto(
        UUID id,
        String flightNumber,
        UUID aircraftId,
        UUID departureAirportId,
        UUID arriveAirportId,
        LocalDateTime flightDate) {
}

