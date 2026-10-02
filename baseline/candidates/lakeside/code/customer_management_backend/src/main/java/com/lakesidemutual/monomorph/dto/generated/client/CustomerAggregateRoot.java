package com.lakesidemutual.monomorph.dto.generated.client;

import com.lakesidemutual.monomorph.dto.generated.proto.customeraggregateroot.*;

import java.util.Objects;

/**
 * Auto-generated DTO gRPC client for
 * {@link CustomerAggregateRoot} and {@link CustomerAggregateRootDTO}.
 */
public final class CustomerAggregateRoot {
    private CustomerAggregateRootDTO dtoInstance;

    public CustomerAggregateRoot() {
        this.dtoInstance = CustomerAggregateRootDTO.newBuilder().build();
    }

    public CustomerAggregateRoot(CustomerId id, CustomerProfileEntity customerProfile) {
        CustomerAggregateRootDTO.Builder builder = CustomerAggregateRootDTO.newBuilder();
        if (id != null) {
            builder.setCustomerId(id.toDTO());
        }
        if (customerProfile != null) {
            builder.setCustomerProfile(customerProfile.toDTO());
        }
        this.dtoInstance = builder.build();
    }

    private CustomerAggregateRoot(CustomerAggregateRootDTO dtoInstance) {
        this.dtoInstance = Objects.requireNonNull(dtoInstance, "dtoInstance");
    }

    public CustomerAggregateRootDTO toDTO() {
        return this.dtoInstance;
    }

    public static CustomerAggregateRoot fromDTO(CustomerAggregateRootDTO dtoInstance) {
        return new CustomerAggregateRoot(dtoInstance);
    }

    /**
     * Original API method alias for the DTO field {@code customerId}.
     */
    public CustomerId getId() {
        return getCustomerId();
    }

    public CustomerId getCustomerId() {
        return CustomerId.fromDTO(dtoInstance.getCustomerId());
    }

    public void setCustomerId(CustomerId customerId) {
        CustomerAggregateRootDTO.Builder builder = dtoInstance.toBuilder();
        if (customerId == null) {
            builder.clearCustomerId();
        } else {
            builder.setCustomerId(customerId.toDTO());
        }
        this.dtoInstance = builder.build();
    }

    public CustomerProfileEntity getCustomerProfile() {
        return CustomerProfileEntity.fromDTO(dtoInstance.getCustomerProfile());
    }

    public void setCustomerProfile(CustomerProfileEntity customerProfile) {
        CustomerAggregateRootDTO.Builder builder = dtoInstance.toBuilder();
        if (customerProfile == null) {
            builder.clearCustomerProfile();
        } else {
            builder.setCustomerProfile(customerProfile.toDTO());
        }
        this.dtoInstance = builder.build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerAggregateRoot)) {
            return false;
        }
        CustomerAggregateRoot that = (CustomerAggregateRoot) o;
        return dtoInstance.equals(that.dtoInstance);
    }

    @Override
    public int hashCode() {
        return dtoInstance.hashCode();
    }

    @Override
    public String toString() {
        return "CustomerAggregateRoot{" + "dtoInstance=" + dtoInstance + '}';
    }
}
