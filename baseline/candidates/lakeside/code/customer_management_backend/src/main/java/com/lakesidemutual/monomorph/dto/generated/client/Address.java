package com.lakesidemutual.monomorph.dto.generated.client;

import com.lakesidemutual.monomorph.dto.generated.proto.address.AddressDTO;

import java.util.Objects;

/**
 * Auto-generated DTO gRPC client for {@code Address} and {@link AddressDTO}.
 *
 * <p>This class uses composition to store all data in an {@link AddressDTO}
 * while exposing the original {@code Address} API.</p>
 */
public class Address {
    private AddressDTO dtoInstance;

    private Address(AddressDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public Address() {
        this.dtoInstance = AddressDTO.newBuilder().build();
    }

    public Address(String streetAddress, String postalCode, String city) {
        this.dtoInstance = AddressDTO.newBuilder()
                .setStreetAddress(streetAddress)
                .setPostalCode(postalCode)
                .setCity(city)
                .build();
    }

    public AddressDTO toDTO() {
        return this.dtoInstance;
    }

    public static Address fromDTO(AddressDTO dtoInstance) {
        return new Address(dtoInstance);
    }

    // --- START OF DTO GETTERS AND SETTERS ---

    public String getStreetAddress() {
        return this.dtoInstance.getStreetAddress();
    }

    public String getPostalCode() {
        return this.dtoInstance.getPostalCode();
    }

    public String getCity() {
        return this.dtoInstance.getCity();
    }

    public void setStreetAddress(String streetAddress) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setStreetAddress(streetAddress)
                .build();
    }

    public void setPostalCode(String postalCode) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setPostalCode(postalCode)
                .build();
    }

    public void setCity(String city) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setCity(city)
                .build();
    }

    // --- END OF DTO GETTERS AND SETTERS ---

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Address other = (Address) obj;
        return Objects.equals(getStreetAddress(), other.getStreetAddress())
                && Objects.equals(getPostalCode(), other.getPostalCode())
                && Objects.equals(getCity(), other.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStreetAddress(), getPostalCode(), getCity());
    }

    @Override
    public String toString() {
        return String.format("%s, %s %s", getStreetAddress(), getPostalCode(), getCity());
    }
}