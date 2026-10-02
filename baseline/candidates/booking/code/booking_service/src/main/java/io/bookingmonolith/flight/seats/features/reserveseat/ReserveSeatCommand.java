package io.bookingmonolith.flight.seats.features.reserveseat;

import buildingblocks.mediator.abstractions.commands.ICommandUnit;

import java.util.UUID;

public record ReserveSeatCommand(String seatNumber, UUID flightId) implements ICommandUnit {
}

