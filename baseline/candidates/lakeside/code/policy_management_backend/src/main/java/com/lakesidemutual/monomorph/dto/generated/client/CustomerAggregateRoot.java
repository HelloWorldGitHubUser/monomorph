package com.lakesidemutual.monomorph.dto.generated.client;

// gRPC imports
import com.lakesidemutual.monomorph.dto.generated.proto.customeraggregateroot.*;

/**
 * Auto-generated DTO gRPC client
 * {@link CustomerAggregateRoot} and {@link CustomerAggregateRootDTO}.
 */
public class CustomerAggregateRoot {
    private CustomerAggregateRootDTO dtoInstance;

    /**
     * Private constructor used by fromDTO; not part of original API.
     */
    private CustomerAggregateRoot(CustomerAggregateRootDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    /**
     * No-argument constructor, matching the original class.
     * Initializes an empty DTO.
     */
    public CustomerAggregateRoot() {
        this.dtoInstance = CustomerAggregateRootDTO.newBuilder().build();
    }

    /**
     * Constructor matching the original class API, adapted for DTO proxies.
     */
    public CustomerAggregateRoot(CustomerId id, CustomerProfileEntity customerProfile) {
        this.dtoInstance = CustomerAggregateRootDTO.newBuilder()
                .setCustomerId(id.toDTO())
                .setCustomerProfile(customerProfile.toDTO())
                .build();
    }

    // Mapping methods
    public CustomerAggregateRootDTO toDTO() {
        return this.dtoInstance;
    }

    public static CustomerAggregateRoot fromDTO(CustomerAggregateRootDTO dtoInstance) {
        return new CustomerAggregateRoot(dtoInstance);
    }

    // Getters and setters for DTO fields, exposing proxy types to maintain original API.

    /**
     * Returns the customer id as a proxy object.
     * Corresponds to the DTO's customerId field.
     */
    public CustomerId getCustomerId() {
        return CustomerId.fromDTO(dtoInstance.getCustomerId());
    }

    public void setCustomerId(CustomerId customerId) {
        dtoInstance = dtoInstance.toBuilder().setCustomerId(customerId.toDTO()).build();
    }

    /**
     * Original API method: returns the customer id.
     */
    public CustomerId getId() {
        return getCustomerId();
    }

    /**
     * Original API method: sets the customer id.
     */
    public void setId(CustomerId id) {
        setCustomerId(id);
    }

    public CustomerProfileEntity getCustomerProfile() {
        return CustomerProfileEntity.fromDTO(dtoInstance.getCustomerProfile());
    }

    public void setCustomerProfile(CustomerProfileEntity customerProfile) {
        dtoInstance = dtoInstance.toBuilder().setCustomerProfile(customerProfile.toDTO()).build();
    }
}