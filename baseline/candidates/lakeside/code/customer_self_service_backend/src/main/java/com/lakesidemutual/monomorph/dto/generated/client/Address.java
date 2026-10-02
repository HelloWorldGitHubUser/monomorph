package com.lakesidemutual.monomorph.dto.generated.client;

// gRPC imports
import com.lakesidemutual.monomorph.dto.generated.proto.address.*;

/**
 * Auto-generated DTO gRPC client for {@link Address} and {@link AddressDTO}.
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
                .setStreetAddress(streetAddress == null ? "" : streetAddress)
                .setPostalCode(postalCode == null ? "" : postalCode)
                .setCity(city == null ? "" : city)
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
    // (none, because no service is defined in the proto file)

    // Implement all other getters and setters corresponding to the DTO fields
    // --- START OF DTO GETTERS AND SETTERS ---
    public String getStreetAddress() {
        return dtoInstance.getStreetAddress();
    }

    public void setStreetAddress(String streetAddress) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setStreetAddress(streetAddress == null ? "" : streetAddress)
                .build();
    }

    public String getPostalCode() {
        return dtoInstance.getPostalCode();
    }

    public void setPostalCode(String postalCode) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setPostalCode(postalCode == null ? "" : postalCode)
                .build();
    }

    public String getCity() {
        return dtoInstance.getCity();
    }

    public void setCity(String city) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setCity(city == null ? "" : city)
                .build();
    }
    // --- END OF DTO GETTERS AND SETTERS ---
}