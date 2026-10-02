package io.bookingmonolith.flight.airports.features.createairport;

import buildingblocks.core.event.DomainEvent;

import java.util.UUID;

public record AirportCreatedDomainEvent(UUID id) implements DomainEvent {
}

