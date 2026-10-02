package io.bookingmonolith.flight.flights.features.updateflight;

import buildingblocks.core.event.DomainEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record FlightUpdatedDomainEvent(
        UUID id,
        String flightNumber,
        UUID aircraftId,
        UUID departureAirportId,
        UUID arriveAirportId,
        LocalDateTime flightDate,
        BigDecimal price) implements DomainEvent {
}

