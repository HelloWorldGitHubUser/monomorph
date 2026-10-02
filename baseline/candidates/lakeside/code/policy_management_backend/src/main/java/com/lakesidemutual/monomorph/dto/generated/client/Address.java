package com.lakesidemutual.monomorph.dto.generated.client;

import com.lakesidemutual.monomorph.dto.generated.proto.address.*;

/**
 * Auto-generated DTO gRPC client
 * {@link Address} and {@link AddressDTO}.
 */
public class Address {
    private AddressDTO dtoInstance;

    public Address(AddressDTO dtoInstance) {
        // dtoConstructor to initialize from a DTO instance
        this.dtoInstance = dtoInstance;
    }

    public Address() {
        this(AddressDTO.newBuilder().build());
    }

    public Address(String streetAddress, String postalCode, String city) {
        this(AddressDTO.newBuilder()
                .setStreetAddress(streetAddress)
                .setPostalCode(postalCode)
                .setCity(city)
                .build());
    }

    // mapping methods
    public AddressDTO toDTO() {
        return this.dtoInstance;
    }

    public static Address fromDTO(AddressDTO dtoInstance) {
        return new Address(dtoInstance);
    }

    // implementation of the gRPC exposed methods
    // No service methods exist for this DTO; only getters are exposed.

    // Implement all other getters and setters corresponding to the DTO fields
    // --- START OF DTO GETTERS AND SETTERS ---
    public String getStreetAddress() {
        return dtoInstance.getStreetAddress();
    }

    public String getPostalCode() {
        return dtoInstance.getPostalCode();
    }

    public String getCity() {
        return dtoInstance.getCity();
    }
    // --- END OF DTO GETTERS AND SETTERS ---

    @Override
    public String toString() {
        return String.format("%s, %s %s", getStreetAddress(), getPostalCode(), getCity());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Address other = (Address) obj;
        return java.util.Objects.equals(getStreetAddress(), other.getStreetAddress())
                && java.util.Objects.equals(getPostalCode(), other.getPostalCode())
                && java.util.Objects.equals(getCity(), other.getCity());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(getStreetAddress(), getPostalCode(), getCity());
    }
}