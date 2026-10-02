package io.bookingmonolith.flight.flights.features.createflight;

import buildingblocks.core.event.DomainEvent;

import java.util.UUID;

public record FlightCreatedDomainEvent(UUID id) implements DomainEvent {
}

