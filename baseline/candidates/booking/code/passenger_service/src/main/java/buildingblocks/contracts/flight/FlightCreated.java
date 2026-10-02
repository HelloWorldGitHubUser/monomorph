package buildingblocks.contracts.flight;

import buildingblocks.core.event.IntegrationEvent;

import java.util.UUID;

public record FlightCreated(UUID id) implements IntegrationEvent {
}

