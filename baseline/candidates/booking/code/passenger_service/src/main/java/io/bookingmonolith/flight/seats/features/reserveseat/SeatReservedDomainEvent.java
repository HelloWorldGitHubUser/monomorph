package io.bookingmonolith.flight.seats.features.reserveseat;

import buildingblocks.core.event.DomainEvent;

import java.util.UUID;

public record SeatReservedDomainEvent(UUID id) implements DomainEvent {
}

