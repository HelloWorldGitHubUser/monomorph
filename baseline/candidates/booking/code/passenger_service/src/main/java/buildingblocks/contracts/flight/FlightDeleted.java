package buildingblocks.contracts.flight;

import buildingblocks.core.event.IntegrationEvent;

import java.util.UUID;

public record FlightDeleted(UUID id) implements IntegrationEvent {
}

