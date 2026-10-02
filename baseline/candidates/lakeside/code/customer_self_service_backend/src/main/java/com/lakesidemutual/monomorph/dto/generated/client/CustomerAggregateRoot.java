package com.lakesidemutual.monomorph.dto.generated.client;

import com.lakesidemutual.monomorph.dto.generated.proto.customeraggregateroot.CustomerAggregateRootDTO;
import java.util.Objects;

/**
 * Auto-generated DTO gRPC client for {@link CustomerAggregateRootDTO}.
 *
 * <p>This class wraps a {@link CustomerAggregateRootDTO} and provides domain-friendly
 * accessors and mutators that convert between {@link CustomerId} and
 * {@link CustomerProfileEntity} value objects and their protobuf representations.
 */
public class CustomerAggregateRoot {

    private CustomerAggregateRootDTO dtoInstance;

    private CustomerAggregateRoot(CustomerAggregateRootDTO dtoInstance) {
        this.dtoInstance = Objects.requireNonNull(dtoInstance, "dtoInstance must not be null");
    }

    public CustomerAggregateRoot() {
        this(CustomerAggregateRootDTO.newBuilder().build());
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

    public CustomerAggregateRootDTO toDTO() {
        return dtoInstance;
    }

    public static CustomerAggregateRoot fromDTO(CustomerAggregateRootDTO dtoInstance) {
        return new CustomerAggregateRoot(dtoInstance);
    }

    public CustomerId getId() {
        if (dtoInstance.hasCustomerId()) {
            return CustomerId.fromDTO(dtoInstance.getCustomerId());
        }
        return null;
    }

    public CustomerProfileEntity getCustomerProfile() {
        if (dtoInstance.hasCustomerProfile()) {
            return CustomerProfileEntity.fromDTO(dtoInstance.getCustomerProfile());
        }
        return null;
    }

    public CustomerId getCustomerId() {
        return getId();
    }

    public void setCustomerId(CustomerId customerId) {
        CustomerAggregateRootDTO.Builder builder = dtoInstance.toBuilder();
        if (customerId == null) {
            builder.clearCustomerId();
        } else {
            builder.setCustomerId(customerId.toDTO());
        }
        dtoInstance = builder.build();
    }

    public void setCustomerProfile(CustomerProfileEntity customerProfile) {
        CustomerAggregateRootDTO.Builder builder = dtoInstance.toBuilder();
        if (customerProfile == null) {
            builder.clearCustomerProfile();
        } else {
            builder.setCustomerProfile(customerProfile.toDTO());
        }
        dtoInstance = builder.build();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CustomerAggregateRoot)) {
            return false;
        }
        CustomerAggregateRoot other = (CustomerAggregateRoot) obj;
        return dtoInstance.equals(other.dtoInstance);
    }

    @Override
    public int hashCode() {
        return dtoInstance.hashCode();
    }

    @Override
    public String toString() {
        return dtoInstance.toString();
    }
}