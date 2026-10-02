package io.bookingmonolith.passenger.passengers.features.createpassenger;

import buildingblocks.core.event.DomainEvent;

import java.util.UUID;

public record PassengerCreatedDomainEvent(UUID id) implements DomainEvent {
}

