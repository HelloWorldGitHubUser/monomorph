package com.lakesidemutual.monomorph.dto.generated.client;

// gRPC imports
import com.lakesidemutual.monomorph.dto.generated.proto.citylookupservice.*;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.lakesidemutual.monomorph.id.generated.helpers.ServiceRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Auto-generated DTO gRPC client
 * {@link CityLookupService} and {@link CityLookupServiceDTO}.
 */
public class CityLookupService {
    private CityLookupServiceDTO dtoInstance;

    // DTO constructor to initialize from a DTO instance.
    // Private to enforce use of fromDTO()/toDTO() for DTO conversion.
    private CityLookupService(CityLookupServiceDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public CityLookupService() {
        this(CityLookupServiceDTO.newBuilder().build());
    }

    // mapping methods
    public CityLookupServiceDTO toDTO() {
        return this.dtoInstance;
    }

    public static CityLookupService fromDTO(CityLookupServiceDTO dtoInstance) {
        return new CityLookupService(dtoInstance);
    }

    // implementation of the gRPC exposed methods

    // TARGET_SERVICE_ID is the unique ID for the ClassA service, provided by the tool
    private static final String TARGET_SERVICE_ID = "customer_core";

    // --- gRPC Specific Fields ---
    private ManagedChannel businessChannel; // Channel for RPC calls
    private CityLookupServiceServiceGrpc.CityLookupServiceServiceBlockingStub businessStub; // Use this stub for RPC calls

    // Helper methods for gRPC
    protected void performRpcSetup() throws Exception {
        // Use the static TARGET_SERVICE_ID to find the endpoint for business logic calls
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort()).usePlaintext().build();
        this.businessStub = CityLookupServiceService.newBlockingStub(businessChannel);
    }

    protected void performSubclassRpcCleanup() {
        // ... shutdown logic for businessChannel ...
        if (this.businessChannel != null && !this.businessChannel.isShutdown()) {
            try {
                this.businessChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                if (!this.businessChannel.isTerminated()) { this.businessChannel.shutdownNow(); }
            } catch (InterruptedException e) { }
        }
    }

    // Implement required methods for gRPC calls here
    // --- START OF gRPC METHOD IMPLEMENTATIONS ---

    /**
     * Returns an alphabetically ordered list of cities that match the given postal code.
     */
    public List<String> getCitiesForPostalCode(String postalCode) {
        if (businessStub == null) {
            try {
                performRpcSetup();
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize gRPC channel for CityLookupService", e);
            }
        }

        String safePostalCode = postalCode == null ? "" : postalCode;
        CityLookupServiceDTO requestDto = CityLookupServiceDTO.newBuilder()
                .setPostalCode(safePostalCode)
                .build();

        GetCitiesForPostalCodeRequest request = GetCitiesForPostalCodeRequest.newBuilder()
                .setDto(requestDto)
                .build();

        GetCitiesForPostalCodeResponse response = businessStub.getCitiesForPostalCode(request);
        return new ArrayList<>(response.getDto().getCitiesList());
    }

    // --- END OF gRPC METHOD IMPLEMENTATIONS ---

    // Implement all other getters and setters corresponding to the DTO fields
    // --- START OF DTO GETTERS AND SETTERS ---

    public String getPostalCode() {
        return dtoInstance.getPostalCode();
    }

    public void setPostalCode(String postalCode) {
        this.dtoInstance = dtoInstance.toBuilder()
                .setPostalCode(postalCode == null ? "" : postalCode)
                .build();
    }

    public List<String> getCities() {
        return new ArrayList<>(dtoInstance.getCitiesList());
    }

    public void setCities(List<String> cities) {
        CityLookupServiceDTO.Builder builder = dtoInstance.toBuilder();
        builder.clearCities();
        if (cities != null) {
            builder.addAllCities(cities);
        }
        this.dtoInstance = builder.build();
    }

    // --- END OF DTO GETTERS AND SETTERS ---
}