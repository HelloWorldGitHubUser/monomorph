package io.bookingmonolith.flight.flights.features.deleteflight;

import buildingblocks.core.event.DomainEvent;

import java.util.UUID;

public record FlightDeletedDomainEvent(UUID id) implements DomainEvent {
}

