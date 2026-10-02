package io.bookingmonolith.booking.bookings.features.createbooking;

import buildingblocks.core.event.DomainEvent;

import java.util.UUID;

public record BookingCreatedDomainEvent(UUID id) implements DomainEvent {
}

