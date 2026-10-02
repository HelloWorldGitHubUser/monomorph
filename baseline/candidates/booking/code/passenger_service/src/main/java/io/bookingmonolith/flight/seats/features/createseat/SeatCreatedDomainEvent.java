package io.bookingmonolith.flight.seats.features.createseat;

import buildingblocks.core.event.DomainEvent;

import java.util.UUID;

public record SeatCreatedDomainEvent(UUID id) implements DomainEvent {
}

